package com.harshit.pharmacy.common.validator;

import com.harshit.pharmacy.exception.BadRequestException;
import com.harshit.pharmacy.exception.ResourceNotFoundException;
import com.harshit.pharmacy.medicine.dto.MedicineInventory;
import com.harshit.pharmacy.medicine.entity.Medicine;
import com.harshit.pharmacy.medicine.enums.BatchStatus;
import com.harshit.pharmacy.medicine.enums.MedicineStatus;
import com.harshit.pharmacy.medicine.repository.MedicineBatchRepository;
import com.harshit.pharmacy.medicine.repository.MedicineRepository;
import com.harshit.pharmacy.order.dto.CartItemRequest;
import com.harshit.pharmacy.order.dto.CheckoutMedicine;
import com.harshit.pharmacy.order.dto.CheckoutRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MedicineValidator {

    private final MedicineRepository medicineRepository;
    private final MedicineBatchRepository medicineBatchRepository;

    public Map<Integer, CheckoutMedicine> validateMedicines(CheckoutRequest request) {

        if (request.items() == null || request.items().isEmpty()) {
            throw new BadRequestException("Cart cannot be empty.");
        }

        // 1. Validate duplicates and map requested quantities
        Map<Integer, Integer> requestedQuantities = new HashMap<>();

        for (CartItemRequest item : request.items()) {

            if (requestedQuantities.put(item.medicineId(), item.quantity()) != null) {

                throw new BadRequestException("Duplicate medicine found in cart: " + item.medicineId());
            }

        }

        List<Integer> medicineIds = new ArrayList<>(requestedQuantities.keySet());

        // 2. Fetch all medicines in one query
        List<Medicine> medicines = medicineRepository.findAllById(medicineIds);
        if (medicines.size() != medicineIds.size()) {
            throw new ResourceNotFoundException("One or more medicines in your cart could not be found.");
        }

        // 3. Fetch inventory details in ONE query (Avoids N+1)
        List<MedicineInventory> inventories = medicineBatchRepository.getMedicineInventories(
                medicineIds,
                BatchStatus.ACTIVE
        );

        Map<Integer, MedicineInventory> inventoryMap = inventories.stream()
                .collect(Collectors.toMap(MedicineInventory::getMedicineId, inv -> inv));

        Map<Integer, CheckoutMedicine> checkoutMap = new HashMap<>();

        // 4. Validate each item
        for (Medicine medicine : medicines) {

            if (medicine.getStatus() != MedicineStatus.ACTIVE) {
                throw new BadRequestException(medicine.getMedicineName() + " is currently inactive/unavailable.");
            }

            MedicineInventory inventory = inventoryMap.get(medicine.getMedicineId());
            int requestedQty = requestedQuantities.get(medicine.getMedicineId());

            if (inventory == null || inventory.getTotalStock() < requestedQty) {
                int available = (inventory != null) ? inventory.getTotalStock() : 0;
                throw new BadRequestException(
                        String.format("Insufficient stock for %s. Requested: %d, Available: %d",
                                medicine.getMedicineName(), requestedQty, available)
                );
            }

            checkoutMap.put(
                    medicine.getMedicineId(),
                    CheckoutMedicine.builder()
                            .medicine(medicine)
                            .sellingPrice(inventory.getSellingPrice())
                            .build()
            );
        }

        return checkoutMap;
    }
}