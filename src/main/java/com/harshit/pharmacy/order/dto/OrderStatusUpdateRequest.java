package com.harshit.pharmacy.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record OrderStatusUpdateRequest(

        @NotBlank(message = "Order status is required.")
        @Pattern(
                regexp = "CONFIRMED|PACKED|OUT_FOR_DELIVERY|DELIVERED",
                message = "Order status must be one of: CONFIRMED, PACKED, OUT_FOR_DELIVERY, DELIVERED.")
        String orderStatus

) {
}
