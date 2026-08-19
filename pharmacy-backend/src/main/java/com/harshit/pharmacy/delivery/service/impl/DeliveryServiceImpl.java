package com.harshit.pharmacy.delivery.service.impl;

import com.harshit.pharmacy.common.constants.ErrorMessages;
import com.harshit.pharmacy.delivery.dto.*;
import com.harshit.pharmacy.delivery.entity.DeliveryBoy;
import com.harshit.pharmacy.delivery.entity.DeliveryStatus;
import com.harshit.pharmacy.delivery.enums.DeliveryBoyStatus;
import com.harshit.pharmacy.delivery.enums.DeliveryStatusEnum;
import com.harshit.pharmacy.delivery.mapper.DeliveryMapper;
import com.harshit.pharmacy.delivery.repository.DeliveryBoyRepository;
import com.harshit.pharmacy.delivery.repository.DeliveryStatusRepository;
import com.harshit.pharmacy.delivery.service.DeliveryService;
import com.harshit.pharmacy.exception.BadRequestException;
import com.harshit.pharmacy.exception.DuplicateResourceException;
import com.harshit.pharmacy.exception.ResourceNotFoundException;
import com.harshit.pharmacy.exception.UnauthorizedException;
import com.harshit.pharmacy.order.dto.OrderResponse;
import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.order.enums.OrderPaymentStatus;
import com.harshit.pharmacy.order.enums.OrderStatus;
import com.harshit.pharmacy.order.mapper.OrderMapper;
import com.harshit.pharmacy.order.repository.OrderRepository;
import com.harshit.pharmacy.payment.service.PaymentService;
import com.harshit.pharmacy.security.utils.SecurityUtils;
import com.harshit.pharmacy.user.entity.User;
import com.harshit.pharmacy.user.enums.UserRole;
import com.harshit.pharmacy.user.enums.UserStatus;
import com.harshit.pharmacy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryBoyRepository deliveryBoyRepository;
    private final DeliveryStatusRepository deliveryStatusRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PaymentService paymentService;
    private final SecurityUtils securityUtils;
    private final DeliveryMapper deliveryMapper;
    private final OrderMapper orderMapper;
    private static final int MAX_ACTIVE_ORDERS = 3;

    @Override
    public DeliveryBoyResponse createDeliveryBoy(CreateDeliveryBoyRequest request) {


        User user = getUser(request.userId());

        validateDeliveryBoyUser(user);

        validateVehicleNumber(request.vehicleNo());

        DeliveryBoy deliveryBoy = deliveryMapper.toDeliveryBoy(user, request);

        deliveryBoyRepository.save(deliveryBoy);

        return deliveryMapper.toDeliveryBoyResponse(deliveryBoy);

    }

    @Override
    public DeliveryBoyResponse updateDeliveryBoy(Integer deliveryBoyId, UpdateDeliveryBoyRequest request) {

        DeliveryBoy deliveryBoy = getDeliveryBoy(deliveryBoyId);

        validateVehicleNumberForUpdate(request.vehicleNo(), deliveryBoy);

        deliveryBoy.setVehicleNo(request.vehicleNo());

        deliveryBoy.setStatus(DeliveryBoyStatus.valueOf(request.status()));

        return deliveryMapper.toDeliveryBoyResponse(deliveryBoyRepository.save(deliveryBoy));

    }


    @Override
    @Transactional(readOnly = true)
    public Page<DeliveryBoyResponse> getAllDeliveryBoys(Pageable pageable) {

        return deliveryBoyRepository.findAll(pageable).map(deliveryMapper::toDeliveryBoyResponse);

    }

    @Override
    public DeliveryStatusResponse assignDeliveryBoy(AssignDeliveryBoyRequest request) {

        Order order = getPackedOrder(request.orderId());

        DeliveryBoy deliveryBoy = getActiveDeliveryBoy(request.deliveryBoyId());

        if (deliveryStatusRepository.existsByOrder(order))
            throw new DuplicateResourceException("Order is already assigned to a delivery boy.");

        if(deliveryStatusRepository.countActiveOrders(
                deliveryBoy.getDeliveryBoyId(),DeliveryStatusEnum.ACTIVE_STATUSES) >= MAX_ACTIVE_ORDERS)
            throw new BadRequestException("Delivery boy has reached maximum active orders.");

        DeliveryStatus deliveryStatus = deliveryMapper.toDeliveryStatus(order, deliveryBoy);

        try {
            deliveryStatusRepository.save(deliveryStatus);
        }
        catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Order is already assigned to a delivery boy.");

        }

        return deliveryMapper.toDeliveryStatusResponse(deliveryStatus);

    }

    @Override
    public DeliveryStatusResponse processDeliveryUpdate(Integer orderId, UpdateDeliveryStatusRequest request) {

        User currentUser = securityUtils.getCurrentUser();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));

        DeliveryStatus deliveryStatus = deliveryStatusRepository.findByOrder(order)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery details not found."));

        if (!deliveryStatus.getDeliveryBoy().getUser().getUserId().equals(currentUser.getUserId()))
            throw new BadRequestException("You are not assigned to this order.");

        DeliveryStatusEnum newStatus = DeliveryStatusEnum.valueOf(request.status());

        validateStatusTransition(deliveryStatus, newStatus);

        deliveryStatus.setCurrentStatus(newStatus);

        if (newStatus == DeliveryStatusEnum.OUT_FOR_DELIVERY)
            order.setOrderStatus(OrderStatus.OUT_FOR_DELIVERY);

        else
            order.setOrderStatus(OrderStatus.DELIVERED);

        deliveryStatusRepository.save(deliveryStatus);
        orderRepository.save(order);

        if(newStatus == DeliveryStatusEnum.DELIVERED &&
                order.getPaymentStatus() == OrderPaymentStatus.PENDING && request.cashCollected())

            paymentService.collectCodPayment(orderId);

        return deliveryMapper.toDeliveryStatusResponse(deliveryStatus);

    }
    @Override
    @Transactional(readOnly = true)
    public DeliveryStatusResponse trackDelivery(Integer orderId){

        User currentUser = securityUtils.getCurrentUser();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));

        if (!order.getUser().getUserId().equals(currentUser.getUserId()))
            throw new BadRequestException("You are not allowed to track this order.");


        DeliveryStatus deliveryStatus = deliveryStatusRepository
                .findByOrder(order)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery details not found."));

        return deliveryMapper.toDeliveryStatusResponse(deliveryStatus);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeliveryBoyResponse> getAvailableDeliveryBoys(Pageable pageable) {

        return deliveryBoyRepository.findAvailableDeliveryBoys(
                        DeliveryBoyStatus.ACTIVE,DeliveryStatusEnum.ACTIVE_STATUSES,
                        MAX_ACTIVE_ORDERS,pageable).map(deliveryMapper::toDeliveryBoyResponse);

    }

    @Override
    @Transactional(readOnly = true)
    public DeliveryStatusResponse getDeliveryStatus(Integer orderId) {
        return deliveryStatusRepository.findByOrderOrderId(orderId)
                .map(deliveryMapper::toDeliveryStatusResponse)
                .orElse(DeliveryStatusResponse.builder().orderId(orderId).build());
    }

    @Override
    public Page<DeliveryStatusResponse> getDeliveryStatusByStatus(String status, Pageable pageable) {

        DeliveryStatusEnum deliveryStatus = DeliveryStatusEnum.valueOf(status.toUpperCase());
        return deliveryStatusRepository.findByCurrentStatus(deliveryStatus, pageable)
                .map(deliveryMapper::toDeliveryStatusResponse);
    }

    @Override
    public void removeDeliveryAssignment(Order order) {

        deliveryStatusRepository.deleteByOrder(order);

    }

    @Override
    public Page<DeliveryStatusResponse> getMyDeliveryOrdersByStatus(String status, Pageable pageable) {

        User currentUser = securityUtils.getCurrentUser();

        DeliveryStatusEnum deliveryStatus;

        deliveryStatus = DeliveryStatusEnum.valueOf(status.toUpperCase());

        return deliveryStatusRepository
                .findByDeliveryBoyUserUserIdAndCurrentStatus(currentUser.getUserId(),
                        deliveryStatus,
                        pageable
                ).map(deliveryMapper::toDeliveryStatusResponse);
    }

    @Override
    public OrderResponse getOrderDetails(Integer orderId) {

        User currentUser = securityUtils.getCurrentUser();

        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                        new ResourceNotFoundException("Order not found."));

        DeliveryStatus deliveryStatus = deliveryStatusRepository.findByOrder(order).orElseThrow(() ->
                                         new ResourceNotFoundException("Delivery details not found."));

        Integer assignedUserId = deliveryStatus.getDeliveryBoy().getUser().getUserId();

        if (!assignedUserId.equals(currentUser.getUserId()))
            throw new UnauthorizedException("You are not assign to this order.");


        return orderMapper.toOrderResponse(order);

    }

    private User getUser(Integer userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    private void validateDeliveryBoyUser(User user) {

        if (user.getRole() != UserRole.DELIVERY_BOY) {
            throw new BadRequestException("Selected user is not a delivery boy.");
        }

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new BadRequestException("Delivery boy account is inactive.");
        }

        if (deliveryBoyRepository.existsByUser(user)) {
            throw new DuplicateResourceException("Delivery boy already exists.");
        }
    }

    private void validateVehicleNumber(String vehicleNo) {

        if (deliveryBoyRepository.existsByVehicleNoIgnoreCase(vehicleNo))

            throw new DuplicateResourceException("Vehicle number already exists.");
    }

    private DeliveryBoy getDeliveryBoy(Integer deliveryBoyId) {

        return deliveryBoyRepository.findById(deliveryBoyId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery boy not found."));
    }

    private void validateVehicleNumberForUpdate(String vehicleNo, DeliveryBoy deliveryBoy) {

        if (deliveryBoy.getVehicleNo().equalsIgnoreCase(vehicleNo)) {
            return;
        }

        if (deliveryBoyRepository.existsByVehicleNoIgnoreCase(vehicleNo)) {
            throw new DuplicateResourceException("Vehicle number already exists.");
        }
    }

    private Order getPackedOrder(Integer orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));

        if (order.getOrderStatus() != OrderStatus.PACKED) {
            throw new BadRequestException("Only packed orders can be assigned.");
        }

        return order;
    }

    private DeliveryBoy getActiveDeliveryBoy(Integer deliveryBoyId) {

        return deliveryBoyRepository
                .findActiveByIdForUpdate(deliveryBoyId,DeliveryBoyStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery boy not found or Inactive"));
    }

    private void validateStatusTransition(DeliveryStatus deliveryStatus, DeliveryStatusEnum newStatus) {

        DeliveryStatusEnum current = deliveryStatus.getCurrentStatus();

        switch (current) {
            case ASSIGNED -> {
                if (newStatus != DeliveryStatusEnum.OUT_FOR_DELIVERY) {
                    throw new BadRequestException("Order must move to OUT_FOR_DELIVERY.");
                }
            }
            case OUT_FOR_DELIVERY -> {
                if (newStatus != DeliveryStatusEnum.DELIVERED) {
                    throw new BadRequestException("Order must move to DELIVERED.");
                }
            }
            case DELIVERED ->
                    throw new BadRequestException("Delivery already completed.");

            default -> throw new BadRequestException("Invalid delivery status transition from: " + current);

        }
    }
}



