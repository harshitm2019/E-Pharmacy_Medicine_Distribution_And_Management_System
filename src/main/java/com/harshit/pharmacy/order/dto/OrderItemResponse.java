package com.harshit.pharmacy.order.dto;

import java.math.BigDecimal;

public record OrderItemResponse(


        Integer medicineId,

        String medicineName,

        Integer quantity,

        BigDecimal subTotal,

        BigDecimal discount,

        BigDecimal tax


) {
}
