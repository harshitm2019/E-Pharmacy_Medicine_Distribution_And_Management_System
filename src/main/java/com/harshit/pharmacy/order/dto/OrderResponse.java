package com.harshit.pharmacy.order.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderResponse(

        Integer orderId,

        BigDecimal totalAmount,

        String orderStatus,

        String paymentStatus,

        String shippingAddress,

        LocalDateTime orderDate,

        List<OrderItemResponse> items


) {
}
