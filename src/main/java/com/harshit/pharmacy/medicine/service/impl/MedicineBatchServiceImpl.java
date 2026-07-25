package com.harshit.pharmacy.medicine.service.impl;


import com.harshit.pharmacy.common.constants.ErrorMessages;
import com.harshit.pharmacy.common.constants.FieldNames;
import com.harshit.pharmacy.common.validator.DuplicateValidator;
import com.harshit.pharmacy.exception.BadRequestException;
import com.harshit.pharmacy.exception.BusinessException;
import com.harshit.pharmacy.exception.ResourceNotFoundException;
import com.harshit.pharmacy.medicine.dto.BatchRequest;
import com.harshit.pharmacy.medicine.dto.BatchResponse;
import com.harshit.pharmacy.medicine.dto.BatchStatusRequest;
import com.harshit.pharmacy.medicine.entity.Medicine;
import com.harshit.pharmacy.medicine.entity.MedicineBatch;
import com.harshit.pharmacy.medicine.enums.BatchStatus;
import com.harshit.pharmacy.medicine.mapper.MedicineBatchMapper;
import com.harshit.pharmacy.medicine.repository.MedicineBatchRepository;
import com.harshit.pharmacy.medicine.repository.MedicineRepository;
import com.harshit.pharmacy.medicine.service.MedicineBatchService;
import com.harshit.pharmacy.redis.service.InventorySyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class MedicineBatchServiceImpl implements MedicineBatchService {


    private final MedicineRepository medicineRepository;
    private final MedicineBatchRepository batchRepository;
    private final DuplicateValidator duplicateValidator;
    private final InventorySyncService inventorySyncService;


    @Override
    public BatchResponse createBatch(BatchRequest request) {


        validateBatchNumberForCreate(request.batchNumber());

        validateManufactureAndExpiryDate(request.manufactureDate(), request.expiryDate());

        Medicine medicine = getMedicine(request.medicineId());

        MedicineBatch batch = batchRepository.save(MedicineBatchMapper.toEntity(request, medicine));

        inventorySyncService.syncMedicineStock(medicine.getMedicineId());

        return MedicineBatchMapper.toResponse(batch);

    }

    @Override
    public BatchResponse updateBatch(Integer batchId, BatchRequest request) {

        MedicineBatch batch = getBatch(batchId);

        validateBatchNumberForUpdate(batch, request.batchNumber());

        validateManufactureAndExpiryDate(request.manufactureDate(), request.expiryDate());

        MedicineBatchMapper.updateEntity(batch, request);

        inventorySyncService.syncMedicineStock(batch.getMedicine().getMedicineId());

        return MedicineBatchMapper.toResponse(batch);

    }

    @Override
    @Transactional(readOnly = true)
    public BatchResponse getBatchById(Integer batchId) {

        return MedicineBatchMapper.toResponse(getBatch(batchId));

    }

    @Override
    @Transactional(readOnly = true)
    public Page<BatchResponse> getBatchesByMedicine(Integer medicineId, Pageable pageable) {

        getMedicine(medicineId);

        return batchRepository.findByMedicineMedicineId(medicineId, pageable)
                .map(MedicineBatchMapper::toResponse);


    }

    @Override
    @Transactional(readOnly = true)
    public Page<BatchResponse> getAllBatches(Pageable pageable) {

        return batchRepository.findAll(pageable)
                .map(MedicineBatchMapper::toResponse);

    }

    @Override
    public List<BatchResponse> updateStatus(BatchStatusRequest request) {

        List<MedicineBatch> batches = batchRepository.findAllById(request.batchIds());

        if (batches.size() != request.batchIds().size()) {
            throw new ResourceNotFoundException(ErrorMessages.BATCH_DOES_NOT_EXIST);
        }

        BatchStatus status = BatchStatus.valueOf(request.status());

        Set<Integer> medicineIds = new HashSet<>();

        for (MedicineBatch batch : batches) {
            batch.setStatus(status);
            medicineIds.add(batch.getMedicine().getMedicineId());
        }

        medicineIds.forEach(inventorySyncService::syncMedicineStock);

        return batches.stream()
                .map(MedicineBatchMapper::toResponse)
                .toList();
    }

    @Override
    public void deductStock(Integer medicineId, Integer quantity) {

        List<MedicineBatch> batches = batchRepository.findByMedicineMedicineIdAndStatusOrderByExpiryDateAsc(
                        medicineId,
                        BatchStatus.ACTIVE
                );

        if (batches.isEmpty()) {
            throw new BadRequestException(
                    "Unable to deduct stock for medicine ID: " + medicineId
            );
        }

        int remainingQuantity = quantity;

        for (MedicineBatch batch : batches) {

            if (remainingQuantity == 0) {
                break;
            }

            int availableStock = batch.getStockQuantity();

            if (availableStock <= remainingQuantity) {

                batch.setStockQuantity(0);
                batch.setStatus(BatchStatus.EXHAUSTED);

                remainingQuantity -= availableStock;

            } else {

                batch.setStockQuantity(availableStock - remainingQuantity);

                remainingQuantity = 0;
            }
        }

        if (remainingQuantity > 0) {

            throw new BadRequestException("Insufficient stock available for medicine ID: " + medicineId);

        }

        inventorySyncService.syncMedicineStock(medicineId);

    }

    @Override
    public void expireBatches() {

        LocalDate cutoffDate = LocalDate.now().plusDays(30);

        List<Integer> affectedMedicineIds = batchRepository
                .findDistinctMedicineIdsByStatusAndExpiryDateLessThanEqual(
                        BatchStatus.ACTIVE,
                        cutoffDate
                );


        if (affectedMedicineIds.isEmpty()) {
            return;
        }

        int rowsAffected = batchRepository.updateStatusForExpiredBatches(
                BatchStatus.ACTIVE,
                BatchStatus.EXPIRED,
                cutoffDate
        );

        affectedMedicineIds.forEach(inventorySyncService::syncMedicineStock);

    }

    private Medicine getMedicine(Integer medicineId) {

        return medicineRepository.findById(medicineId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.MEDICINE_DOES_NOT_EXIST));

    }

    private MedicineBatch getBatch(Integer batchId) {

        return batchRepository.findById(batchId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.BATCH_DOES_NOT_EXIST));

    }

    private void validateBatchNumberForCreate(String batchNumber) {

        duplicateValidator.validate(
                batchRepository.existsByBatchNumberIgnoreCase(batchNumber.trim()),
                FieldNames.BATCH_NUMBER
        );

    }

    private void validateBatchNumberForUpdate(MedicineBatch batch, String batchNumber) {

        batchRepository.findByBatchNumberIgnoreCase(batchNumber.trim()).ifPresent(existingBatch ->

                        duplicateValidator.validate(
                                !existingBatch.getBatchId().equals(batch.getBatchId()),
                                FieldNames.BATCH_NUMBER
                        )

                );

    }

    private void validateManufactureAndExpiryDate(LocalDate manufactureDate, LocalDate expiryDate) {

        if (!expiryDate.isAfter(manufactureDate)) {
            throw new BusinessException(ErrorMessages.INVALID_EXPIRY_DATE);
        }

    }



}
