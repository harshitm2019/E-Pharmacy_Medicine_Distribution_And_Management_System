package com.harshit.pharmacy.payment.mapper;

import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.payment.dto.PaymentResponse;
import com.harshit.pharmacy.payment.entity.Payment;
import com.harshit.pharmacy.payment.enums.PaymentMethod;
import com.harshit.pharmacy.payment.enums.PaymentStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PaymentMapper {


    public Payment toEntity(Order order,String paymentMethod,PaymentStatus paymentStatus) {

           return Payment.builder()
                .order(order)
                .paymentMethod(PaymentMethod.valueOf(paymentMethod.toUpperCase()))
                .paymentStatus(paymentStatus)
                .amount(order.getTotalAmount())
                .paidDate(LocalDateTime.now())
                .build();
    }

    public PaymentResponse toResponse(Payment payment) {

        return PaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .paymentMethod(payment.getPaymentMethod().name())
                .paymentStatus(payment.getPaymentStatus().name())
                .amount(payment.getAmount())
                .orderId(payment.getOrder().getOrderId())
                .paidDate(payment.getPaidDate())
                .build();

    }

}
