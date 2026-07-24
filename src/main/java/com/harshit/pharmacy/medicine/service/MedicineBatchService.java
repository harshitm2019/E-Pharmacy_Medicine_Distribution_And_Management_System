package com.harshit.pharmacy.medicine.service;

import com.harshit.pharmacy.medicine.dto.BatchRequest;
import com.harshit.pharmacy.medicine.dto.BatchResponse;
import com.harshit.pharmacy.medicine.dto.BatchStatusRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MedicineBatchService {

    BatchResponse createBatch(BatchRequest request);

    BatchResponse updateBatch(Integer batchId, BatchRequest request);

    BatchResponse getBatchById(Integer batchId);

    Page<BatchResponse> getBatchesByMedicine(Integer medicineId, Pageable pageable);

    Page<BatchResponse> getAllBatches(Pageable pageable);

    List<BatchResponse> updateStatus(BatchStatusRequest request);

    void deductStock(Integer medicineId, Integer quantity);

    void expireBatches();
}
