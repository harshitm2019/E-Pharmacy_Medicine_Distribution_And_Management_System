package com.harshit.pharmacy.returns.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateReturnStatusRequest(

        @NotBlank(message = "Return status is required.")
        @Pattern(
                regexp = "^(APPROVED|REJECTED|REFUNDED)$",
                message = "Return status must be APPROVED, REJECTED or REFUNDED."
        )
        String returnStatus


) {
}
