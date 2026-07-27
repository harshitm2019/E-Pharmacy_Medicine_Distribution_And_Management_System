package com.harshit.pharmacy.payment.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record PaymentResponse(


        Integer paymentId,

        Integer orderId,

        String paymentMethod,

        String paymentStatus,

        BigDecimal amount,

        LocalDateTime paidDate



) {
}
