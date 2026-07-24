package com.harshit.pharmacy.order.dto;

import com.harshit.pharmacy.medicine.entity.Medicine;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CheckoutMedicine(

        Medicine medicine,

        BigDecimal sellingPrice

) {
}
