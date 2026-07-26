package com.harshit.pharmacy.prescription.controller;

import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.response.ApiResponse;
import com.harshit.pharmacy.common.swagger.annotations.prescription.GetMyPrescriptionsApi;
import com.harshit.pharmacy.common.swagger.annotations.prescription.GetPrescriptionByIdApi;
import com.harshit.pharmacy.common.swagger.annotations.prescription.ReplacePrescriptionApi;
import com.harshit.pharmacy.common.swagger.annotations.prescription.UploadPrescriptionApi;
import com.harshit.pharmacy.common.swagger.constants.SwaggerConstants;
import com.harshit.pharmacy.prescription.dto.PrescriptionResponse;
import com.harshit.pharmacy.prescription.dto.UploadPrescriptionRequest;
import com.harshit.pharmacy.prescription.service.PrescriptionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = SwaggerConstants.Prescription_TAG, description = "APIs for managing Prescription")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;


    @UploadPrescriptionApi
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<PrescriptionResponse>> uploadPrescription(
            @RequestPart("prescription") MultipartFile prescription,
            @Valid @RequestPart("request") UploadPrescriptionRequest request) {

        PrescriptionResponse response = prescriptionService.uploadPrescription(prescription, request);

        return ResponseEntity.status(CREATED).body(
                ApiResponse.success(SuccessMessages.PRESCRIPTION_UPLOADED, response)
        );
    }


    @ReplacePrescriptionApi
    @PutMapping(value = "/{prescriptionId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<PrescriptionResponse>> replacePrescription(
            @PathVariable Integer prescriptionId,
            @RequestPart("prescription")
            MultipartFile prescription) {

        PrescriptionResponse response = prescriptionService.replacePrescription(prescriptionId, prescription);

        return ResponseEntity.status(OK).body(

                ApiResponse.success(SuccessMessages.PRESCRIPTION_REPLACED,response)

        );

    }

    @GetMyPrescriptionsApi
    @GetMapping
    public ResponseEntity<ApiResponse<Page<PrescriptionResponse>>> getMyPrescriptions(
            @PageableDefault(sort = "uploadedDate")
            Pageable pageable) {

        Page<PrescriptionResponse> response = prescriptionService.getMyPrescriptions(pageable);

        return ResponseEntity.status(OK).body(

                ApiResponse.success(SuccessMessages.PRESCRIPTION_FETCHED_SUCCESSFULLY,response)
        );
    }


    @GetPrescriptionByIdApi
    @GetMapping("/{prescriptionId}")
    public ResponseEntity<ApiResponse<PrescriptionResponse>> getPrescriptionById(@PathVariable Integer prescriptionId) {


        PrescriptionResponse response =  prescriptionService.getPrescriptionById(prescriptionId);

        return ResponseEntity.status(OK).body(

                ApiResponse.success(SuccessMessages.PRESCRIPTION_FETCHED_SUCCESSFULLY,response)

        );

    }

}