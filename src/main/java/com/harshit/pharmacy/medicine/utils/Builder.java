package com.harshit.pharmacy.medicine.utils;

import com.harshit.pharmacy.medicine.dto.MedicineInventory;
import com.harshit.pharmacy.medicine.dto.MedicineResponse;
import com.harshit.pharmacy.medicine.entity.Medicine;
import com.harshit.pharmacy.medicine.enums.BatchStatus;
import com.harshit.pharmacy.medicine.mapper.MedicineMapper;
import com.harshit.pharmacy.medicine.repository.MedicineBatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Builder {

    private final MedicineBatchRepository medicineBatchRepository;

    public MedicineResponse buildMedicineResponse(Medicine medicine) {

        MedicineInventory inventory = medicineBatchRepository.getMedicineInventory(
                medicine.getMedicineId(),
                BatchStatus.ACTIVE
        );

        int stock = (inventory != null && inventory.getTotalStock() != null) ? inventory.getTotalStock() : 0;
        BigDecimal price = (inventory != null) ? inventory.getSellingPrice() : null;

        return MedicineMapper.toResponse(
                medicine,
                stock,
                price,
                stock > 0
        );

    }

    public Page<MedicineResponse> buildMedicineResponses(Page<Medicine> medicinePage) {

        if (medicinePage.isEmpty()) {
            return Page.empty(medicinePage.getPageable());
        }

        Map<Integer, MedicineInventory> inventoryMap = getInventoryMap(
                medicinePage.getContent()
                        .stream()
                        .map(Medicine::getMedicineId)
                        .toList()
        );

        return medicinePage.map(medicine ->
                buildMedicineResponse(
                        medicine,
                        inventoryMap.get(medicine.getMedicineId())
                )
        );
    }

    public List<MedicineResponse> buildMedicineResponses(List<Medicine> medicines) {

        if (medicines.isEmpty()) {
            return List.of();
        }

        Map<Integer, MedicineInventory> inventoryMap = getInventoryMap(
                medicines.stream()
                        .map(Medicine::getMedicineId)
                        .toList()
        );

        return medicines.stream()
                .map(medicine -> buildMedicineResponse(
                        medicine,
                        inventoryMap.get(medicine.getMedicineId())
                ))
                .toList();
    }

    private MedicineResponse buildMedicineResponse(Medicine medicine, MedicineInventory inventory) {

        int stock = (inventory != null && inventory.getTotalStock() != null) ? inventory.getTotalStock() : 0;

        BigDecimal price = inventory != null ? inventory.getSellingPrice() : null;

        return MedicineMapper.toResponse(
                medicine,
                stock,
                price,
                stock > 0
        );
    }

    private Map<Integer, MedicineInventory> getInventoryMap(List<Integer> medicineIds) {

        return medicineBatchRepository
                .getMedicineInventories(medicineIds, BatchStatus.ACTIVE)
                .stream()
                .collect(Collectors.toMap(
                        MedicineInventory::getMedicineId,
                        Function.identity()
                ));
    }



}
