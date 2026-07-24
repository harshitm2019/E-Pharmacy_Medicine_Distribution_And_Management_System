package com.harshit.pharmacy.medicine.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record BatchStatusRequest(

        @NotEmpty(message = "Batch ids are required.")
        List<Integer> batchIds,

        @NotBlank(message = "Status is required.")
        @Pattern(
                regexp = "^(ACTIVE|EXHAUSTED|EXPIRED)$",
                message = "Status must be ACTIVE, EXHAUSTED or EXPIRED."
        )
        String status

) {
}