package com.harshit.pharmacy.order.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record OrderReportResponse(
        Integer orderId,
        String customerName,
        LocalDateTime orderDate,
        BigDecimal totalAmount,
        String status
) {}