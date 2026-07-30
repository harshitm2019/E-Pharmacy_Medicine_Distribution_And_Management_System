package com.harshit.pharmacy.delivery.dto;
import jakarta.validation.constraints.NotNull;

public record AssignDeliveryBoyRequest(

        @NotNull(message = "Order id is required.")
        Integer orderId,

        @NotNull(message = "Delivery boy id is required.")
        Integer deliveryBoyId

) {
}
