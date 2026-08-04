package com.harshit.pharmacy.returns.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateReturnRequest(


        @NotNull(message = "Order id is required.")
        Integer orderId,

        @NotBlank(message = "Return reason is required.")
        @Size(max = 500, min = 10,message = "Return reason cannot exceed 500 characters.")
        String returnReason


) {
}
