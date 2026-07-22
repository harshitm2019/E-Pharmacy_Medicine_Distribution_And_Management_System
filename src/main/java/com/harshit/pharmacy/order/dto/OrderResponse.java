package com.harshit.pharmacy.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
