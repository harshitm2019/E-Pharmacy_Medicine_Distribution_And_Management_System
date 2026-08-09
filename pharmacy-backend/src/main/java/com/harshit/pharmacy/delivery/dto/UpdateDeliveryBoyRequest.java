package com.harshit.pharmacy.delivery.dto;

import com.harshit.pharmacy.delivery.enums.DeliveryBoyStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateDeliveryBoyRequest(


        @NotBlank(message = "Vehicle number is required.")
        @Pattern(
                regexp = "^[A-Z]{2}[0-9]{2}[A-Z]{1,2}[0-9]{4}$",
                message = "Invalid vehicle number."
        )
        String vehicleNo,

        @NotNull(message = "Status is required.")
        @Pattern(
                regexp = "^(INACTIVE|ACTIVE)$",
                message = "Status should be ACTIVE or INACTIVE"
        )
        String status


) {
}
