package com.harshit.pharmacy.prescription.dto;

import com.harshit.pharmacy.prescription.enums.PrescriptionStatus;

import java.time.LocalDateTime;

public record PrescriptionResponse(


        Integer prescriptionId,

        String doctorName,

        PrescriptionStatus status,

        String prescriptionUrl,

        LocalDateTime uploadedDate



) {
}
