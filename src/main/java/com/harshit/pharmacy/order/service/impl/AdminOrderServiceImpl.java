package com.harshit.pharmacy.order.service.impl;

import com.harshit.pharmacy.exception.BadRequestException;
import com.harshit.pharmacy.exception.ResourceNotFoundException;
import com.harshit.pharmacy.order.dto.OrderResponse;
import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.order.enums.OrderPaymentStatus;
import com.harshit.pharmacy.order.enums.OrderStatus;
import com.harshit.pharmacy.order.mapper.OrderMapper;
import com.harshit.pharmacy.order.repository.OrderRepository;
import com.harshit.pharmacy.order.service.AdminOrderService;
import com.harshit.pharmacy.order.service.OrderService;
import com.harshit.pharmacy.payment.entity.Payment;
import com.harshit.pharmacy.payment.enums.PaymentMethod;
import com.harshit.pharmacy.payment.repository.PaymentRepository;
import com.harshit.pharmacy.prescription.entity.Prescription;
import com.harshit.pharmacy.prescription.enums.PrescriptionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class AdminOrderServiceImpl implements AdminOrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> getPendingOrders(Pageable pageable) {

        return orderRepository.findByOrderStatusAndPrescriptionStatus(
                        OrderStatus.PENDING,
                        PrescriptionStatus.PENDING,
                        pageable)
                .map(OrderMapper::toOrderResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getPendingOrder(Integer orderId) {

        Order order = orderRepository.findByOrderIdAndOrderStatusAndPrescriptionStatus(
                        orderId, OrderStatus.PENDING, PrescriptionStatus.PENDING)
                .       orElseThrow(() -> new ResourceNotFoundException("Pending order not found."));

        return OrderMapper.toOrderResponse(order);

    }

    @Override
    public void updatePrescriptionStatus(Integer orderId, String status) {


        Order order = orderRepository.findByOrderIdAndOrderStatus(orderId, OrderStatus.PENDING)
                     .orElseThrow(() ->
                        new ResourceNotFoundException("Pending order not found."));

        Prescription prescription = order.getPrescription();

        if (prescription == null)
            throw new BadRequestException("No prescription found.");

        if (prescription.getStatus() != PrescriptionStatus.PENDING) {
            throw new BadRequestException("Prescription has already been processed.");
        }

        prescription.setStatus(PrescriptionStatus.valueOf(status));

        if (prescription.getStatus() == PrescriptionStatus.APPROVED) {

            Payment payment = paymentRepository.findByOrder(order)
                            .orElseThrow(() ->
                            new ResourceNotFoundException("Payment not found."));

            if (payment.getPaymentMethod() == PaymentMethod.COD) {

                orderService.confirmOrder(orderId);

            } else if (order.getPaymentStatus() == OrderPaymentStatus.PAID) {

                orderService.confirmOrder(orderId);

            }
        }

    }

    @Override
    public void changeOrderStatus(Integer orderId, String status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));

        OrderStatus newStatus = OrderStatus.valueOf(status);

        validateOrderStatusTransition(order.getOrderStatus(), newStatus);

        order.setOrderStatus(OrderStatus.valueOf(status));

    }

    @Override
    public void cancelOrder(Integer orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));

        orderService.processOrderCancellation(order);

    }

    private void validateOrderStatusTransition(OrderStatus current,
                                               OrderStatus next) {

        switch (current) {
            case PENDING -> {
                if (next != OrderStatus.CONFIRMED) {
                    throw new BadRequestException("Invalid order status transition.");
                }
            }
            case CONFIRMED -> {
                if (next != OrderStatus.PACKED) {
                    throw new BadRequestException("Invalid order status transition.");
                }
            }
            case PACKED -> {
                if (next != OrderStatus.OUT_FOR_DELIVERY) {
                    throw new BadRequestException("Invalid order status transition.");
                }
            }
            case OUT_FOR_DELIVERY -> {
                if (next != OrderStatus.DELIVERED) {
                    throw new BadRequestException("Invalid order status transition.");
                }
            }
            default -> throw new BadRequestException("Order cannot be updated.");
        }
    }

}
