package com.harshit.pharmacy.common.validator;


import com.harshit.pharmacy.exception.BadRequestException;
import com.harshit.pharmacy.exception.ResourceNotFoundException;
import com.harshit.pharmacy.medicine.entity.Medicine;
import com.harshit.pharmacy.medicine.enums.MedicineStatus;
import com.harshit.pharmacy.medicine.repository.MedicineRepository;
import com.harshit.pharmacy.order.dto.CartItemRequest;
import com.harshit.pharmacy.order.dto.CheckoutRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MedicineValidator {

    private final MedicineRepository medicineRepository;

    public Map<Integer, Medicine> validateMedicines(CheckoutRequest request) {

        List<Integer> medicineIds = request.items()
                .stream()
                .map(CartItemRequest::medicineId)
                .toList();

        List<Medicine> medicines = medicineRepository.findAllById(medicineIds);

        if (medicines.size() != medicineIds.size()) {
            throw new ResourceNotFoundException("One or more medicines not found.");
        }

        Map<Integer, Medicine> medicineMap = new HashMap<>();

        for (Medicine medicine : medicines) {

            if (medicine.getStatus() != MedicineStatus.ACTIVE) {
                throw new BadRequestException(
                        medicine.getMedicineName() + " is currently unavailable."
                );
            }

            if (medicine.getExpiryDate().isBefore(LocalDate.now())) {
                throw new BadRequestException(
                        medicine.getMedicineName() + " has expired."
                );
            }

            medicineMap.put(medicine.getMedicineId(), medicine);
        }

        return medicineMap;
    }



}
