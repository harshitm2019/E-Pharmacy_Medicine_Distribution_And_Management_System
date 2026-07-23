package com.harshit.pharmacy.order.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CheckoutResponse(



        Integer orderId,

        String reservationId,

        BigDecimal totalAmount,

        String orderStatus,

        String paymentStatus,

        String message




) {
}
