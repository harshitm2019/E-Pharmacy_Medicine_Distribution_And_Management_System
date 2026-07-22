package com.harshit.pharmacy.payment.service;

import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.payment.entity.Payment;

public interface PaymentService {

    boolean processPayment(String paymentMethod);

    Payment savePayment(Order order,
                        String paymentMethod);

}
