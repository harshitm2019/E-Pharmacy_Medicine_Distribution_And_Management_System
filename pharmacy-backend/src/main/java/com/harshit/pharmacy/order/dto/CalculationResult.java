package com.harshit.pharmacy.order.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record CalculationResult (


        BigDecimal totalAmount,

        List<OrderItemCalculation> orderItems

){
}
