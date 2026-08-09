package com.harshit.pharmacy.delivery.dto;

import lombok.Builder;

@Builder
public record DeliveryBoyResponse(

         Integer deliveryBoyId,
         Integer userId,
         String vehicleNo,
         String status

) {
}
