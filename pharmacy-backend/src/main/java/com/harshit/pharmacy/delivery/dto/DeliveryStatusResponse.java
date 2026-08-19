package com.harshit.pharmacy.delivery.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record DeliveryStatusResponse(

        Integer orderId,
        String deliveryStatus,
        LocalDateTime assignedDate,
        LocalDate expectedDeliveryDate,
        Integer deliveryBoyId,
        String deliveryBoyName,
        String vehicleNo

) {
}
