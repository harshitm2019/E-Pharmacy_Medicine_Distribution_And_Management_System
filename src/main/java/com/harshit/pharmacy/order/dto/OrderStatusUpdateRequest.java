package com.harshit.pharmacy.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record OrderStatusUpdateRequest(

        @NotBlank(message = "Order status is required.")
        @Pattern(
                regexp = "CONFIRMED|PACKED",
                message = "Order status must be one of: CONFIRMED, PACKED")
        String orderStatus

) {
}
