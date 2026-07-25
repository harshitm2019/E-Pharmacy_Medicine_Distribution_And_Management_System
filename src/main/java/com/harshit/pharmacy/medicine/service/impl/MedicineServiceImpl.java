package com.harshit.pharmacy.medicine.service.impl;

import com.harshit.pharmacy.category.entity.Category;
import com.harshit.pharmacy.category.repository.CategoryRepository;
import com.harshit.pharmacy.common.constants.ErrorMessages;
import com.harshit.pharmacy.exception.ResourceNotFoundException;
import com.harshit.pharmacy.medicine.dto.MedicineInventory;
import com.harshit.pharmacy.medicine.dto.MedicineRequest;
import com.harshit.pharmacy.medicine.dto.MedicineResponse;
import com.harshit.pharmacy.medicine.dto.MedicineStatusRequest;
import com.harshit.pharmacy.medicine.entity.Medicine;
import com.harshit.pharmacy.medicine.enums.BatchStatus;
import com.harshit.pharmacy.medicine.enums.MedicineStatus;
import com.harshit.pharmacy.medicine.mapper.MedicineMapper;
import com.harshit.pharmacy.medicine.repository.MedicineBatchRepository;
import com.harshit.pharmacy.medicine.repository.MedicineRepository;
import com.harshit.pharmacy.medicine.service.MedicineService;
import com.harshit.pharmacy.medicine.utils.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;
    private final CategoryRepository categoryRepository;
    private final Builder builder;

    @Override
    public MedicineResponse createMedicine(MedicineRequest request) {


        Category category = getCategory(request.categoryId());

        Medicine medicine = medicineRepository.save(
                MedicineMapper.toEntity(request, category)
        );

        return MedicineMapper.toResponse(
                medicine,
                0,
                null,
                false
        );

    }

    @Override
    public MedicineResponse updateMedicine(Integer medicineId, MedicineRequest request) {

        Medicine medicine = getMedicine(medicineId);

        Category category = getCategory(request.categoryId());

        MedicineMapper.updateEntity(medicine, request, category);

        return builder.buildMedicineResponse(medicine);
    }


    @Override
    @Transactional(readOnly = true)
    public MedicineResponse getMedicineById(Integer medicineId) {

        return builder.buildMedicineResponse(getMedicine(medicineId));

    }


    @Override
    @Transactional(readOnly = true)
    public Page<MedicineResponse> getAllActiveMedicines(Pageable pageable) {


        return builder.buildMedicineResponses(
                medicineRepository.findByStatus(
                        MedicineStatus.ACTIVE,
                        pageable
                )
        );

    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicineResponse> getAllMedicines(Pageable pageable) {

        return builder.buildMedicineResponses(
                medicineRepository.findAll(pageable)
        );

    }


    @Override
    public List<MedicineResponse> updateStatus(MedicineStatusRequest request) {

        List<Medicine> medicines = medicineRepository.findAllById(request.medicineIds());

        if (medicines.size() != request.medicineIds().size())
            throw new ResourceNotFoundException(ErrorMessages.MEDICINE_DOES_NOT_EXIST);


        medicines.forEach(medicine -> medicine.setStatus(MedicineStatus.valueOf(request.status()))
        );

        return builder.buildMedicineResponses(medicines);

    }

    @Override
    @Transactional(readOnly = true)
    public MedicineResponse getActiveMedicineById(Integer medicineId) {

        Medicine medicine = medicineRepository.findByMedicineIdAndStatus(
                medicineId,
                MedicineStatus.ACTIVE).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessages.MEDICINE_DOES_NOT_EXIST));

        return builder.buildMedicineResponse(medicine);

    }

    private Medicine getMedicine(Integer medicineId) {

        return medicineRepository.findById(medicineId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorMessages.MEDICINE_DOES_NOT_EXIST));

    }


    @Override
    @Transactional(readOnly = true)
    public Page<MedicineResponse> searchActiveMedicines(String keyword, Pageable pageable) {

        return builder.buildMedicineResponses(
                medicineRepository.findByMedicineNameContainingIgnoreCaseAndStatus(
                        keyword.trim(),
                        MedicineStatus.ACTIVE,
                        pageable
                )
        );

    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicineResponse> searchMedicines(String keyword, Pageable pageable) {

        return builder.buildMedicineResponses(
                medicineRepository.findByMedicineNameContainingIgnoreCase(
                        keyword.trim(),
                        pageable
                )
        );

    }

    private Category getCategory(Integer categoryId) {

        return categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorMessages.CATEGORY_DOES_NOT_EXIST));

    }

}
