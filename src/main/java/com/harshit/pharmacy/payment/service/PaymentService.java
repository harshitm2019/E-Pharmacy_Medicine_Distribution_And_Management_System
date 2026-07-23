package com.harshit.pharmacy.payment.service;

import com.harshit.pharmacy.payment.dto.PaymentResponse;
import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.payment.entity.Payment;

import java.util.List;

public interface PaymentService {

    boolean processPayment(String paymentMethod);

    Payment savePayment(Order order,
                        String paymentMethod);


    List<PaymentResponse> getMyPayments();

    PaymentResponse getPayment(Integer paymentId);
}
