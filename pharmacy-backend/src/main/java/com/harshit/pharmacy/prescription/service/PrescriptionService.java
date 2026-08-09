package com.harshit.pharmacy.prescription.service;

import com.harshit.pharmacy.prescription.dto.PrescriptionResponse;
import com.harshit.pharmacy.prescription.dto.UploadPrescriptionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PrescriptionService {

    PrescriptionResponse uploadPrescription(MultipartFile prescription, UploadPrescriptionRequest request);

    Page<PrescriptionResponse> getMyPrescriptions(Pageable pageable);

    PrescriptionResponse replacePrescription(Integer prescriptionId, MultipartFile file);

    PrescriptionResponse getPrescriptionById(Integer prescriptionId);

}
