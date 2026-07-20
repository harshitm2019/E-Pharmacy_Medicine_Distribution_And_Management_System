package com.harshit.pharmacy.prescription.record;

import com.harshit.pharmacy.prescription.enums.PrescriptionStatus;

import java.time.LocalDateTime;

public record PrescriptionResponse(


        Integer prescriptionId,

        String doctorName,

        PrescriptionStatus status,

        LocalDateTime uploadedDate

) {
}
