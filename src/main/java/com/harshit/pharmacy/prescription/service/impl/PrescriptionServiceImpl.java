package com.harshit.pharmacy.prescription.service.impl;

import com.harshit.pharmacy.common.constants.ErrorMessages;
import com.harshit.pharmacy.common.storage.StorageService;
import com.harshit.pharmacy.common.validator.FileValidator;
import com.harshit.pharmacy.exception.BadRequestException;
import com.harshit.pharmacy.exception.ResourceNotFoundException;
import com.harshit.pharmacy.exception.UnauthorizedException;
import com.harshit.pharmacy.prescription.entity.Prescription;
import com.harshit.pharmacy.prescription.enums.PrescriptionStatus;
import com.harshit.pharmacy.prescription.mapper.PrescriptionMapper;
import com.harshit.pharmacy.prescription.dto.PrescriptionResponse;
import com.harshit.pharmacy.prescription.dto.UploadPrescriptionRequest;
import com.harshit.pharmacy.prescription.repository.PrescriptionRepository;
import com.harshit.pharmacy.prescription.service.PrescriptionService;
import com.harshit.pharmacy.security.utils.SecurityUtils;
import com.harshit.pharmacy.user.entity.User;
import com.harshit.pharmacy.user.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final StorageService storageService;
    private final FileValidator fileValidator;
    private final SecurityUtils securityUtils;
    private final PrescriptionMapper prescriptionMapper;


    @Override
    public PrescriptionResponse uploadPrescription(MultipartFile prescriptionFile, UploadPrescriptionRequest request) {


        fileValidator.validatePrescription(prescriptionFile);

        User currentUser = securityUtils.getCurrentUser();

        String filePath = storageService.uploadPrescription(prescriptionFile);

        Prescription prescription = prescriptionMapper.toEntity(request,filePath,currentUser);

        Prescription savedPrescription = prescriptionRepository.save(prescription);

        return prescriptionMapper.toResponse(savedPrescription);

    }
    @Override
    public PrescriptionResponse replacePrescription(Integer prescriptionId, MultipartFile file) {

        Prescription prescription = getOwnedPrescription(prescriptionId);

        if (prescription.getStatus() != PrescriptionStatus.PENDING &&
                prescription.getStatus() != PrescriptionStatus.REJECTED) {
            throw new BadRequestException(ErrorMessages.CANNOT_MODIFY_REVIEWED_PRESCRIPTION);
        }

        fileValidator.validatePrescription(file);

        String oldFilePath = prescription.getFilePath();

        String newFilePath = storageService.uploadPrescription(file);

        try {

            prescription.setFilePath(newFilePath);
            prescription.setStatus(PrescriptionStatus.PENDING);

            Prescription updatedPrescription = prescriptionRepository.save(prescription);

            storageService.deletePrescription(oldFilePath);

            return prescriptionMapper.toResponse(updatedPrescription);

        } catch (Exception ex) {

            storageService.deletePrescription(newFilePath);
            throw new BadRequestException(ErrorMessages.UNABLE_TO_REPLACE_PRESCRIPTION);

        }

    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrescriptionResponse> getMyPrescriptions(Pageable pageable) {

        User currentUser = securityUtils.getCurrentUser();

        return prescriptionRepository.findByUser(currentUser, pageable).map(prescriptionMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public PrescriptionResponse getPrescriptionById(Integer prescriptionId) {

        Prescription prescription = getOwnedPrescription(prescriptionId);

        return prescriptionMapper.toResponse(prescription);

    }

    private Prescription getOwnedPrescription(Integer prescriptionId) {

        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PRESCRIPTION_NOT_FOUND));

        User currentUser = securityUtils.getCurrentUser();

        boolean isOwner = prescription.getUser().getUserId().equals(currentUser.getUserId());
        boolean isAdmin = currentUser.getRole() == UserRole.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new UnauthorizedException(ErrorMessages.ACCESS_DENIED);
        }

        return prescription;

    }


}
