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
import com.harshit.pharmacy.medicine.dto.MedicineRequest;
import com.harshit.pharmacy.medicine.dto.MedicineResponse;
import com.harshit.pharmacy.medicine.dto.MedicineStatusRequest;
import com.harshit.pharmacy.medicine.repository.MedicineRepository;
import com.harshit.pharmacy.medicine.service.MedicineService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

        validateActiveMedicineForCreate(request);

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
    public Page<MedicineResponse> getAllActiveMedicinesByCategory(Integer categoryId, Pageable pageable){

        return medicineRepository.findByStatusAndCategoryCategoryId(
                                     MedicineStatus.ACTIVE,
                                     categoryId,
                                     pageable).map(MedicineMapper::toResponse);
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

        MedicineStatus status = MedicineStatus.valueOf(request.status());

        if (status == MedicineStatus.ACTIVE)
            validateActiveMedicine(medicines);

        medicines.forEach(medicine -> medicine.setStatus(MedicineStatus.valueOf(request.status())));

        return medicineRepository.saveAll(medicines)
                .stream()
                .map(MedicineMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public Page<MedicineResponse> searchMedicines(String keyword, Pageable pageable) {

        return medicineRepository.findByMedicineNameContainingIgnoreCase(

                        keyword.trim(),
                        pageable)
                .map(MedicineMapper::toResponse);

    }

    @Override
    public Page<MedicineResponse> filterMedicines(Integer categoryId, MedicineStatus status, Pageable pageable) {

        Page<Medicine> medicines;

        if (categoryId != null && status != null) {
            medicines = medicineRepository.findByStatusAndCategoryCategoryId(status, categoryId, pageable);
        } else if (categoryId != null) {
            medicines = medicineRepository.findByCategoryCategoryId(categoryId, pageable);
        } else if (status != null) {
            medicines = medicineRepository.findByStatus(status, pageable);
        } else {
            medicines = medicineRepository.findAll(pageable);
        }

        return medicines.map(MedicineMapper::toResponse);

    }

    private Medicine getMedicine(Integer medicineId) {

        return medicineRepository.findById(medicineId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorMessages.MEDICINE_DOES_NOT_EXIST));

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

    private void validateActiveMedicineForCreate(MedicineRequest request) {

        if (medicineRepository.existsByMedicineNameIgnoreCaseAndStatus(
                        request.medicineName().trim(),
                        MedicineStatus.ACTIVE)
        ) {

            throw new BusinessException("An active batch already exists for this medicine.");

        }
    }

    private void validateActiveMedicine(List<Medicine> medicines) {

        for (Medicine medicine : medicines) {

            if (medicine.getExpiryDate() != null) {
                LocalDate today = LocalDate.now();
                long daysUntilExpiry = ChronoUnit.DAYS.between(today, medicine.getExpiryDate());

                if (daysUntilExpiry < 30) {
                    throw new BusinessException("Cannot activate medicine '" + medicine.getMedicineName()
                            + "' because it expires in less than 30 days (" + daysUntilExpiry + " days remaining).");
                }
            }

            if (medicineRepository.existsByMedicineNameIgnoreCaseAndStatusAndMedicineIdNot(
                    medicine.getMedicineName(),
                    MedicineStatus.ACTIVE,
                    medicine.getMedicineId())) {

                throw new BusinessException("An active batch already exists for medicine: "
                                + medicine.getMedicineName());
            }
        }
    }


}

