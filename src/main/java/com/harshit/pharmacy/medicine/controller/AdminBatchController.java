package com.harshit.pharmacy.medicine.controller;

import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.response.ApiResponse;
import com.harshit.pharmacy.common.swagger.annotations.medicinebatch.*;
import com.harshit.pharmacy.common.swagger.constants.SwaggerConstants;
import com.harshit.pharmacy.medicine.dto.BatchRequest;
import com.harshit.pharmacy.medicine.dto.BatchResponse;
import com.harshit.pharmacy.medicine.dto.BatchStatusRequest;
import com.harshit.pharmacy.medicine.service.MedicineBatchService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = SwaggerConstants.BATCH_TAG, description = "APIs for managing medicine batches. Accessible only to administrators.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/batches")
public class AdminBatchController {

    private final MedicineBatchService medicineBatchService;

    @CreateBatchApi
    @PostMapping
    public ResponseEntity<ApiResponse<BatchResponse>> createBatch(
            @Valid @RequestBody BatchRequest request) {

        BatchResponse response = medicineBatchService.createBatch(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(SuccessMessages.BATCH_CREATED, response)
        );
    }

    @UpdateBatchApi
    @PutMapping("/{batchId}")
    public ResponseEntity<ApiResponse<BatchResponse>> updateBatch(
            @PathVariable Integer batchId,
            @Valid @RequestBody BatchRequest request) {

        BatchResponse response = medicineBatchService.updateBatch(batchId, request);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.BATCH_UPDATED, response)
        );
    }

  @GetBatchByIdApi
    @GetMapping("/{batchId}")
    public ResponseEntity<ApiResponse<BatchResponse>> getBatchById(
            @PathVariable Integer batchId) {

        BatchResponse response = medicineBatchService.getBatchById(batchId);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.BATCH_FETCHED, response)
        );
    }

    @GetAllBatchesApi
    @GetMapping
    public ResponseEntity<ApiResponse<Page<BatchResponse>>> getAllBatches(
            @ParameterObject Pageable pageable) {

        Page<BatchResponse> response = medicineBatchService.getAllBatches(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.ALL_BATCHES_FETCHED, response)
        );
    }

    @GetBatchesByMedicineApi
    @GetMapping("/medicine/{medicineId}")
    public ResponseEntity<ApiResponse<Page<BatchResponse>>> getBatchesByMedicine(
            @PathVariable Integer medicineId,
            @ParameterObject Pageable pageable) {

        Page<BatchResponse> response =
                medicineBatchService.getBatchesByMedicine(medicineId, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.ALL_BATCHES_FETCHED, response)
        );
    }

    @UpdateBatchStatusApi
    @PatchMapping("/status")
    public ResponseEntity<ApiResponse<List<BatchResponse>>> updateStatus(
            @Valid @RequestBody BatchStatusRequest request) {

        List<BatchResponse> response = medicineBatchService.updateStatus(request);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.BATCH_STATUS_UPDATED, response)
        );
    }
}