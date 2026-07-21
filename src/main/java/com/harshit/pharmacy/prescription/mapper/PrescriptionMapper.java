package com.harshit.pharmacy.prescription.mapper;

import com.harshit.pharmacy.common.storage.StorageService;
import com.harshit.pharmacy.prescription.entity.Prescription;
import com.harshit.pharmacy.prescription.enums.PrescriptionStatus;
import com.harshit.pharmacy.prescription.record.PrescriptionResponse;
import com.harshit.pharmacy.prescription.record.UploadPrescriptionRequest;
import com.harshit.pharmacy.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrescriptionMapper {

    private final StorageService storageService;


    public Prescription toEntity(UploadPrescriptionRequest request, String filePath, User user) {

        return Prescription.builder()
                .user(user)
                .doctorName(request.doctorName())
                .status(PrescriptionStatus.PENDING)
                .filePath(filePath)
                .build();

    }

    public PrescriptionResponse toResponse(Prescription prescription) {

        return new PrescriptionResponse(
                prescription.getPrescriptionId(),
                prescription.getDoctorName(),
                prescription.getStatus(),
                getUrl(prescription.getFilePath()),
                prescription.getUploadedDate()
        );

    }

    private String getUrl(String filePath) {

        return storageService.getFileUrl(filePath);
    }

}
