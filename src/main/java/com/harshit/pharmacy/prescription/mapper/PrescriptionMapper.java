package com.harshit.pharmacy.prescription.mapper;

import com.harshit.pharmacy.prescription.entity.Prescription;
import com.harshit.pharmacy.prescription.enums.PrescriptionStatus;
import com.harshit.pharmacy.prescription.record.PrescriptionResponse;
import com.harshit.pharmacy.prescription.record.UploadPrescriptionRequest;
import org.springframework.stereotype.Component;

@Component
public class PrescriptionMapper {


    public Prescription toEntity(UploadPrescriptionRequest request) {

        return Prescription.builder()
                .doctorName(request.doctorName())
                .status(PrescriptionStatus.PENDING)
                .build();

    }

    public PrescriptionResponse toResponse(Prescription prescription) {

        return new PrescriptionResponse(
                prescription.getPrescriptionId(),
                prescription.getDoctorName(),
                prescription.getStatus(),
                prescription.getUploadedDate()
        );

    }


}
