package com.harshit.pharmacy.payment.service;

import com.harshit.pharmacy.payment.dto.PaymentRequest;
import com.harshit.pharmacy.payment.dto.PaymentResponse;
import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.payment.entity.Payment;
import com.harshit.pharmacy.payment.enums.PaymentStatus;

import java.util.List;

public interface PaymentService {

    PaymentResponse processPayment(PaymentRequest request);

    Payment savePayment(Order order, String paymentMethod, PaymentStatus paymentStatus);

    List<PaymentResponse> getMyPayments();

    PaymentResponse getPayment(Integer paymentId);
}
