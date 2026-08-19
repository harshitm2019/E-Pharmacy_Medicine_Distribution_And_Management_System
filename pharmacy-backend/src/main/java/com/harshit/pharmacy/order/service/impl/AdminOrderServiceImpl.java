package com.harshit.pharmacy.order.service.impl;

import com.harshit.pharmacy.exception.BadRequestException;
import com.harshit.pharmacy.exception.BusinessException;
import com.harshit.pharmacy.exception.ResourceNotFoundException;
import com.harshit.pharmacy.order.dto.OrderReportResponse;
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
import com.harshit.pharmacy.payment.enums.PaymentStatus;
import com.harshit.pharmacy.payment.repository.PaymentRepository;
import com.harshit.pharmacy.prescription.entity.Prescription;
import com.harshit.pharmacy.prescription.enums.PrescriptionStatus;
import com.harshit.pharmacy.prescription.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class    AdminOrderServiceImpl implements AdminOrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Override
    public Page<OrderResponse> getOrders(String status, Pageable pageable) {


        return orderRepository.findByStatus(status, pageable)
                .map(orderMapper::toOrderResponse);

    }

    @Override
    public void updatePrescriptionStatus(Integer orderId, String status) {

        Order order = orderRepository.findByOrderIdAndOrderStatus(orderId, OrderStatus.PENDING)
                        .orElseThrow(() ->
                        new ResourceNotFoundException("Pending order not found."));

        Prescription prescription = order.getPrescription();

        if (prescription == null)
            throw new BadRequestException("No prescription found.");

        if (prescription.getStatus() != PrescriptionStatus.PENDING)
            throw new BadRequestException("Prescription has already been processed.");

        prescription.setStatus(PrescriptionStatus.valueOf(status));

        prescriptionRepository.save(prescription);

        if (prescription.getStatus() == PrescriptionStatus.APPROVED) {

            Optional<Payment> paymentOptional = paymentRepository.findByOrder(order);

            if (paymentOptional.isPresent()
                    && paymentOptional.get().getPaymentMethod() == PaymentMethod.COD)

                orderService.confirmOrder(orderId);
            
            else if (order.getPaymentStatus() == OrderPaymentStatus.PAID)

                orderService.confirmOrder(orderId);

        }

    }

    @Override
    public void changeOrderStatus(Integer orderId, String status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));

        OrderStatus newStatus = OrderStatus.valueOf(status);
        validateOrderStatusTransition(order.getOrderStatus(), newStatus);

        if (order.getOrderStatus() == OrderStatus.PENDING) {

            if (order.getPrescription() != null && order.getPrescription().getStatus() == PrescriptionStatus.PENDING) {
                throw new BusinessException("Order cannot be confirmed until prescription is approved.");
            }

            if (order.getPaymentStatus() == OrderPaymentStatus.PENDING) {
                throw new BusinessException("Order cannot be confirmed because payment is pending.");
            }

            orderService.confirmOrder(orderId);
        }

        order.setOrderStatus(newStatus);

        orderRepository.save(order);

    }

    @Override
    public void cancelOrder(Integer orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));

        orderService.processOrderCancellation(order);

    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));

        return orderMapper.toOrderResponse(order);
    }

    @Override
    public Page<OrderReportResponse> getOrderReport(LocalDate startDate, LocalDate endDate, Pageable pageable) {

        if (startDate.isAfter(endDate)) {
            throw new BadRequestException("Start date cannot be after end date.");
        }

        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.plusDays(1).atStartOfDay();

        return orderRepository
                .findByOrderDateGreaterThanEqualAndOrderDateLessThan(start, end, pageable)
                .map(orderMapper::toOrderReportResponse);

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
            default -> throw new BadRequestException("Order cannot be updated.");
        }
    }

}
