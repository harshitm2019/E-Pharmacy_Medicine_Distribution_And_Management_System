package com.harshit.pharmacy.medicine.service.impl;

import com.harshit.pharmacy.category.entity.Category;
import com.harshit.pharmacy.category.repository.CategoryRepository;
import com.harshit.pharmacy.common.constants.ErrorMessages;
import com.harshit.pharmacy.common.constants.FieldNames;
import com.harshit.pharmacy.common.validator.DuplicateValidator;
import com.harshit.pharmacy.exception.BusinessException;
import com.harshit.pharmacy.exception.ResourceNotFoundException;
import com.harshit.pharmacy.medicine.entity.Medicine;
import com.harshit.pharmacy.medicine.enums.MedicineStatus;
import com.harshit.pharmacy.medicine.mapper.MedicineMapper;
import com.harshit.pharmacy.medicine.record.MedicineRequest;
import com.harshit.pharmacy.medicine.record.MedicineResponse;
import com.harshit.pharmacy.medicine.record.MedicineStatusRequest;
import com.harshit.pharmacy.medicine.repository.MedicineRepository;
import com.harshit.pharmacy.medicine.service.MedicineService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;
    private final CategoryRepository categoryRepository;
    private final DuplicateValidator duplicateValidator;

    @Override
    public MedicineResponse createMedicine(MedicineRequest request) {

        validateBatchNumberForCreate(request.batchNumber());

        Category category = getCategory(request.categoryId());

        validateDates(request.manufactureDate(), request.expiryDate());

        return MedicineMapper.toResponse(
                medicineRepository.save(
                        MedicineMapper.toEntity(request, category)
                )
        );


    }

    @Override
    public MedicineResponse updateMedicine(Integer medicineId, MedicineRequest request) {


        Medicine medicine = getMedicine(medicineId);

        validateBatchNumberForUpdate(medicine, request.batchNumber());

        Category category = getCategory(request.categoryId());

        validateDates(request.manufactureDate(), request.expiryDate());

        MedicineMapper.updateEntity(medicine, request, category);

        return MedicineMapper.toResponse(medicineRepository.save(medicine));

    }


    @Override
    @Transactional(readOnly = true)
    public MedicineResponse getMedicineById(Integer medicineId) {

        return MedicineMapper.toResponse(getMedicine(medicineId));

    }


    @Override
    @Transactional(readOnly = true)
    public Page<MedicineResponse> getAllActiveMedicines(Pageable pageable) {

        return medicineRepository.findByStatus(MedicineStatus.ACTIVE, pageable)
                .map(MedicineMapper::toResponse);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicineResponse> getAllMedicines(Pageable pageable) {

        return medicineRepository.findAll(pageable)
                .map(MedicineMapper::toResponse);

    }


    @Override
    public List<MedicineResponse> updateStatus(MedicineStatusRequest request) {

        List<Medicine> medicines = medicineRepository.findAllById(request.medicineIds());

        if (medicines.size() != request.medicineIds().size())
            throw new ResourceNotFoundException(ErrorMessages.MEDICINE_DOES_NOT_EXIST);

        medicines.forEach(medicine -> medicine.setStatus(MedicineStatus.valueOf(request.status())));

        return medicineRepository.saveAll(medicines)
                .stream()
                .map(MedicineMapper::toResponse)
                .toList();


    }

    @Override
    @Transactional(readOnly = true)
    public MedicineResponse getActiveMedicineById(Integer medicineId) {

        return MedicineMapper.toResponse(

                medicineRepository.findByMedicineIdAndStatus(
                        medicineId,
                        MedicineStatus.ACTIVE
                ).orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.MEDICINE_DOES_NOT_EXIST))

        );

    }

    private Medicine getMedicine(Integer medicineId) {

        return medicineRepository.findById(medicineId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorMessages.MEDICINE_DOES_NOT_EXIST));

    }


    @Override
    public Page<MedicineResponse> searchActiveMedicines(String keyword, Pageable pageable) {

        return medicineRepository
                .findByMedicineNameContainingIgnoreCaseAndStatus(
                        keyword.trim(),
                        MedicineStatus.ACTIVE,
                        pageable
                )
                .map(MedicineMapper::toResponse);

    }

    @Override
    public Page<MedicineResponse> searchMedicines(String keyword, Pageable pageable) {

        return medicineRepository.findByMedicineNameContainingIgnoreCase(

                 keyword.trim(),
                 pageable)
                .map(MedicineMapper::toResponse);

    }

    private Category getCategory(Integer categoryId) {

        return categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorMessages.CATEGORY_DOES_NOT_EXIST));

    }

    private void validateBatchNumberForCreate(String batchNumber) {

        duplicateValidator.validate(
                medicineRepository.existsByBatchNumberIgnoreCase(batchNumber.trim()),
                FieldNames.BATCH_NUMBER
        );

    }

    private void validateBatchNumberForUpdate(Medicine medicine, String batchNumber) {

        medicineRepository.findByBatchNumberIgnoreCase(batchNumber.trim())
                .ifPresent(existingMedicine -> {

                        duplicateValidator.validate(!existingMedicine.getMedicineId().equals(medicine.getMedicineId()),
                                FieldNames.BATCH_NUMBER);

                });

    }

    private void validateDates(LocalDate manufactureDate, LocalDate expiryDate) {

        if (!expiryDate.isAfter(manufactureDate))
            throw new BusinessException(ErrorMessages.INVALID_EXPIRY_DATE);

    }


}
