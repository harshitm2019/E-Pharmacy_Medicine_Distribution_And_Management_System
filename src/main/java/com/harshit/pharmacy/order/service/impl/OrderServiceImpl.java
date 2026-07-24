package com.harshit.pharmacy.order.service.impl;

import com.harshit.pharmacy.common.validator.MedicineValidator;
import com.harshit.pharmacy.common.validator.PrescriptionValidator;
import com.harshit.pharmacy.exception.BadRequestException;
import com.harshit.pharmacy.exception.ResourceNotFoundException;
import com.harshit.pharmacy.medicine.entity.Medicine;
import com.harshit.pharmacy.medicine.repository.MedicineRepository;
import com.harshit.pharmacy.order.dto.CheckoutMedicine;
import com.harshit.pharmacy.order.dto.CheckoutRequest;
import com.harshit.pharmacy.order.dto.CheckoutResponse;
import com.harshit.pharmacy.order.dto.OrderResponse;
import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.order.entity.OrderItem;
import com.harshit.pharmacy.order.enums.OrderStatus;
import com.harshit.pharmacy.order.mapper.OrderMapper;
import com.harshit.pharmacy.order.repository.OrderItemRepository;
import com.harshit.pharmacy.order.repository.OrderRepository;
import com.harshit.pharmacy.order.service.OrderService;
import com.harshit.pharmacy.order.utils.Builder;
import com.harshit.pharmacy.order.utils.Calculate;
import com.harshit.pharmacy.payment.enums.PaymentMethod;
import com.harshit.pharmacy.payment.service.PaymentService;
import com.harshit.pharmacy.prescription.entity.Prescription;
import com.harshit.pharmacy.prescription.service.PrescriptionService;
import com.harshit.pharmacy.redis.dto.ReservationRequest;
import com.harshit.pharmacy.redis.dto.ReservationResponse;
import com.harshit.pharmacy.redis.service.StockReservationService;
import com.harshit.pharmacy.security.utils.SecurityUtils;
import com.harshit.pharmacy.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {


    private final StockReservationService stockReservationService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final SecurityUtils securityUtils;
    private final MedicineValidator medicineValidator;
    private final PrescriptionValidator prescriptionValidator;

    @Override
    public CheckoutResponse checkout(CheckoutRequest request) {

        User user = securityUtils.getCurrentUser();

        Map<Integer, CheckoutMedicine> medicines = medicineValidator.validateMedicines(request);

        Prescription prescription = prescriptionValidator.validatePrescription(request, user, medicines);

        ReservationRequest reservationRequest = Builder.buildReservationRequest(user.getUserId(), request, medicines);

        ReservationResponse reservationResponse = stockReservationService.reserveStock(reservationRequest);

        if (!reservationResponse.success())
            throw new BadRequestException(reservationResponse.message());

        if (PaymentMethod.COD.name().equalsIgnoreCase(request.paymentMethod()))

            stockReservationService.confirmReservation(reservationRequest.reservationId());

        BigDecimal totalAmount = Calculate.calculateTotalAmount(request, medicines);

        Order order = orderMapper.toEntity(user, request.shippingAddress(), totalAmount, prescription);

        List<OrderItem> orderItems = Builder.buildOrderItems(order, request, medicines);

        order.getOrderItems().addAll(orderItems);

        Order savedOrder = null;

        try {

            savedOrder = orderRepository.save(order);

        } catch (Exception ex) {

            stockReservationService.releaseReservation(reservationResponse.reservationId());
            throw new BadRequestException(ex.getMessage());

        }

        return orderMapper.toCheckoutResponse(savedOrder, reservationResponse.reservationId());

    }


    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getMyOrders() {

        User currentUser = securityUtils.getCurrentUser();

        return orderRepository.findByUser(currentUser)
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Integer orderId) {

        User currentUser = securityUtils.getCurrentUser();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found with id : " + orderId));

        if (!order.getUser().getUserId().equals(currentUser.getUserId())) {
            throw new ResourceNotFoundException("Order not found with id : " + orderId);
        }

        return orderMapper.toResponse(order);
    }

    @Override
    public void cancelOrder(Integer orderId) {

        User currentUser = securityUtils.getCurrentUser();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found with id : " + orderId));

        if (!order.getUser().getUserId().equals(currentUser.getUserId())) {
            throw new ResourceNotFoundException("Order not found with id : " + orderId);
        }

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Only pending orders can be cancelled.");
        }

        order.setOrderStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);
    }
}