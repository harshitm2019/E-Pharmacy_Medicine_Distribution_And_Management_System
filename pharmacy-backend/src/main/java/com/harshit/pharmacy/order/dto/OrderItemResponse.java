package com.harshit.pharmacy.order.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemResponse(


        Integer medicineId,

        String medicineName,

        Integer quantity,

        BigDecimal subTotal,

        BigDecimal discount,

        BigDecimal tax


) {
}
