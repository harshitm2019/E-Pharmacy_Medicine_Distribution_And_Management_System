package com.harshit.pharmacy.payment.service.impl;

import com.harshit.pharmacy.exception.ResourceNotFoundException;
import com.harshit.pharmacy.payment.dto.PaymentResponse;
import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.payment.entity.Payment;
import com.harshit.pharmacy.payment.enums.PaymentMethod;
import com.harshit.pharmacy.payment.enums.PaymentStatus;
import com.harshit.pharmacy.payment.mapper.PaymentMapper;
import com.harshit.pharmacy.payment.repository.PaymentRepository;
import com.harshit.pharmacy.payment.service.PaymentService;
import com.harshit.pharmacy.security.utils.SecurityUtils;
import com.harshit.pharmacy.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final SecurityUtils securityUtils;
    private final PaymentMapper paymentMapper;

    @Override
    public boolean processPayment(String paymentMethod) {

        // Payment gateway simulation
        return true;
    }

    @Override
    public Payment savePayment(Order order, String paymentMethod) {

        Payment payment = paymentMapper.toEntity(order, paymentMethod);

        return paymentRepository.save(payment);

    }

    @Override
    public List<PaymentResponse> getMyPayments() {

        User currentUser = securityUtils.getCurrentUser();

        return paymentRepository.findByOrder_User(currentUser)
                .stream()
                .map(paymentMapper::toResponse)
                .toList();
    }

    @Override
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
