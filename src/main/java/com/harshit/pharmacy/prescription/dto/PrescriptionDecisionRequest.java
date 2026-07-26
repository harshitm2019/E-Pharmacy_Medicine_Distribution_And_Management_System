package com.harshit.pharmacy.prescription.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PrescriptionDecisionRequest(

        @NotBlank(message = "Prescription status is required.")
        @Pattern(regexp = "^(APPROVED|REJECTED)$" ,
                 message = "Prescription status must be APPROVED or REJECTED")
        String prescriptionStatus

) {
}
