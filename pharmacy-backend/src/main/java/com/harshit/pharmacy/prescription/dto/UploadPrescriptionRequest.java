package com.harshit.pharmacy.prescription.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UploadPrescriptionRequest(


        @NotBlank(message = "Doctor name is required")
        @Size(max = 200, message = "Doctor name must not exceed 200 characters")
        String doctorName

) {
}
