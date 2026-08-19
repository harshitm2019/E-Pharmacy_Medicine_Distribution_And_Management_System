package com.harshit.pharmacy.delivery.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateDeliveryBoyRequest(

        @NotNull(message = "User id is required.")
        Integer userId,

        @NotBlank(message = "Vehicle number is required.")
        @Pattern(
                regexp = "^[A-Z]{2}[0-9]{2}[A-Z]{1,2}[0-9]{4}$",
                message = "Invalid vehicle number."
        )
        String vehicleNo

) {
}
