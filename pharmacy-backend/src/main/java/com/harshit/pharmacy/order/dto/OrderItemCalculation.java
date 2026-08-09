package com.harshit.pharmacy.order.dto;

import com.harshit.pharmacy.medicine.entity.Medicine;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemCalculation(


        Medicine medicine,

        Integer quantity,

        BigDecimal subTotal,

        BigDecimal discount,

        BigDecimal tax


) {
}
