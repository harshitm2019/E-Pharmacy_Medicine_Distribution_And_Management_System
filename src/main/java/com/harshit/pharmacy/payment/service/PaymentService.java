package com.harshit.pharmacy.payment.service;

import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.payment.dto.PaymentRequest;
import com.harshit.pharmacy.payment.dto.PaymentResponse;
import com.harshit.pharmacy.payment.entity.Payment;

import java.util.List;

public interface PaymentService {

    PaymentResponse processOnlinePayment(PaymentRequest request);

    void collectCodPayment(Integer orderId);

    List<PaymentResponse> getMyPayments();

    PaymentResponse getPayment(Integer paymentId);

    void createCodPayment(Order order);

    void updatePaymentMethod(Order order, String paymentMethod);

}
