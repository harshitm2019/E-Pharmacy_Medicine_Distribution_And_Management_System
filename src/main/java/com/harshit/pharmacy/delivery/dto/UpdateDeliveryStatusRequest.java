package com.harshit.pharmacy.delivery.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateDeliveryStatusRequest(

        @NotBlank(message = "Delivery status is required.")
        @Pattern(
                regexp = "^(ASSIGNED|OUT_FOR_DELIVERY|DELIVERED)$",
                message = "Delivery status must be ASSIGNED, OUT_FOR_DELIVERY, or DELIVERED."
        )
        String status,

        boolean cashCollected



) {
}
