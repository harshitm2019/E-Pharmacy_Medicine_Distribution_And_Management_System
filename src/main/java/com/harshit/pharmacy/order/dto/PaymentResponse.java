package com.harshit.pharmacy.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(


        Integer paymentId,

        Integer orderId,

        String paymentMethod,

        String paymentStatus,

        BigDecimal amount,

        LocalDateTime paidDate


) {
}
