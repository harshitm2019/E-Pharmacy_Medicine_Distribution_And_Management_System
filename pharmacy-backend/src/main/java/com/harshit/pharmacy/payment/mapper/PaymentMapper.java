package com.harshit.pharmacy.payment.mapper;

import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.payment.dto.PaymentResponse;
import com.harshit.pharmacy.payment.entity.Payment;
import com.harshit.pharmacy.payment.enums.PaymentMethod;
import com.harshit.pharmacy.payment.enums.PaymentStatus;

public class PaymentMapper {


    public static PaymentResponse toPaymentResponse(Payment payment) {

        return PaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .orderId(payment.getOrder().getOrderId())
                .amount(payment.getAmount())
                .paymentMethod(String.valueOf(payment.getPaymentMethod()))
                .paymentStatus(String.valueOf(payment.getPaymentStatus()))
                .paidDate(payment.getPaidDate())
                .build();
    }

    public static Payment buildCodPayment(Order order) {


         return Payment.builder().
                 order(order).
                 paymentMethod(PaymentMethod.COD).
                 paymentStatus(PaymentStatus.PENDING).
                 amount(order.getTotalAmount()).build();


    }

    public static Payment buildOnlinePayment(Order order,
                                             PaymentMethod paymentMethod) {

        return Payment.builder()
                .order(order)
                .amount(order.getTotalAmount())
                .paymentMethod(paymentMethod)
                .paymentStatus(PaymentStatus.PENDING)
                .build();
    }


}
