package com.harshit.pharmacy.order.dto;

import java.math.BigDecimal;

public record CheckoutResponse(


        Integer orderId,

        BigDecimal totalAmount,

        String orderStatus,

        String paymentStatus,

        String message



) {
}
