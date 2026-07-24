package com.harshit.pharmacy.payment.service.impl;

import com.harshit.pharmacy.common.validator.PaymentValidator;
import com.harshit.pharmacy.exception.PaymentFailedException;
import com.harshit.pharmacy.exception.ResourceNotFoundException;
import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.order.enums.OrderPaymentStatus;
import com.harshit.pharmacy.order.repository.OrderRepository;
import com.harshit.pharmacy.payment.dto.PaymentRequest;
import com.harshit.pharmacy.payment.dto.PaymentResponse;
import com.harshit.pharmacy.payment.entity.Payment;
import com.harshit.pharmacy.payment.enums.PaymentStatus;
import com.harshit.pharmacy.payment.mapper.PaymentMapper;
import com.harshit.pharmacy.payment.repository.PaymentRepository;
import com.harshit.pharmacy.payment.service.PaymentService;
import com.harshit.pharmacy.redis.service.StockReservationService;
import com.harshit.pharmacy.security.utils.SecurityUtils;
import com.harshit.pharmacy.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final SecurityUtils securityUtils;
    private final PaymentMapper paymentMapper;
    private final PaymentValidator paymentValidator;
    private final StockReservationService stockReservationService;
    private final OrderRepository orderRepository;

    @Override
    public PaymentResponse processPayment(PaymentRequest  paymentRequest) {

        User user = securityUtils.getCurrentUser();

        Order order = paymentValidator.validatePayment(
                paymentRequest.orderId(),
                user
        );

        boolean paymentSuccessful = true;

        if (!paymentSuccessful)

            throw new PaymentFailedException("Payment failed.");


        stockReservationService.confirmReservation(paymentRequest.reservationId());

        order.setPaymentStatus(OrderPaymentStatus.PAID);

        Payment payment = savePayment(
                order,
                paymentRequest.paymentMethod(),
                PaymentStatus.PAID
        );

        orderRepository.save(order);

        return paymentMapper.toResponse(payment);

    }

    @Override
    public Payment savePayment(Order order, String paymentMethod,PaymentStatus paymentStatus) {

        Payment payment = paymentMapper.toEntity(order, paymentMethod, paymentStatus);

        return paymentRepository.save(payment);

    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> getMyPayments() {

        User currentUser = securityUtils.getCurrentUser();

        return paymentRepository.findByOrder_User(currentUser)
                .stream()
                .map(paymentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPayment(Integer paymentId) {

        User currentUser = securityUtils.getCurrentUser();

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found with id : " + paymentId));

        if (!payment.getOrder().getUser().getUserId().equals(currentUser.getUserId())) {
            throw new ResourceNotFoundException("Payment not found with id : " + paymentId);
        }

        return paymentMapper.toResponse(payment);
    }



}
