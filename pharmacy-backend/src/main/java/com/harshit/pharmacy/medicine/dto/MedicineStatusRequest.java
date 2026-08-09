package com.harshit.pharmacy.medicine.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record MedicineStatusRequest(


        @NotEmpty(message = "Medicine ids are required.")
        List<Integer> medicineIds,

        @NotBlank(message = "Status is required.")
        @Pattern(regexp = "^(ACTIVE|INACTIVE)$", message = "Status must be ACTIVE or INACTIVE.")
        String status


) {
}
