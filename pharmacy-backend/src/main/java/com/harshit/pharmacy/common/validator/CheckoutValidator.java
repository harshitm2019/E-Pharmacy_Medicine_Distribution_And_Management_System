package com.harshit.pharmacy.common.validator;

import com.harshit.pharmacy.exception.BadRequestException;
import com.harshit.pharmacy.exception.ResourceNotFoundException;
import com.harshit.pharmacy.medicine.entity.Medicine;
import com.harshit.pharmacy.medicine.enums.MedicineStatus;
import com.harshit.pharmacy.medicine.enums.PrescriptionNeed;
import com.harshit.pharmacy.order.dto.CartItemRequest;
import com.harshit.pharmacy.prescription.entity.Prescription;
import com.harshit.pharmacy.prescription.enums.PrescriptionStatus;
import com.harshit.pharmacy.prescription.repository.PrescriptionRepository;
import com.harshit.pharmacy.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
@RequiredArgsConstructor
public class CheckoutValidator {

    private final PrescriptionRepository prescriptionRepository;

    public void validateDuplicateMedicineIds(List<CartItemRequest> items) {

        Set<Integer> medicineIds = new HashSet<>();

        for (CartItemRequest item : items) {

            if (!medicineIds.add(item.medicineId())) {
                throw new BadRequestException(
                        "Duplicate medicine found in cart."
                );
            }

        }

    }

    public Prescription validatePrescription(User user, Integer prescriptionId, Collection<Medicine> medicines) {

        boolean prescriptionRequired = medicines.stream()
                .anyMatch(medicine ->
                        medicine.getPrescriptionNeed() == PrescriptionNeed.YES);

        if (!prescriptionRequired) {
            return null;
        }

        if (prescriptionId == null) {
            throw new BadRequestException("Prescription is required.");
        }

        Prescription prescription = prescriptionRepository
                .findByPrescriptionIdAndUser(
                        prescriptionId,
                        user
                )
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found."));

        if (prescription.getStatus() == PrescriptionStatus.REJECTED) {
            throw new BadRequestException("Prescription has been rejected.");
        }

        return prescription;

    }

    public Map<Integer, Medicine> validateMedicines(
            List<CartItemRequest> cartItems,
            List<Medicine> medicines
    ) {

        if (cartItems.size() != medicines.size()) {

            throw new ResourceNotFoundException("One or more medicines not found.");
        }

        Map<Integer, Medicine> medicineMap = new HashMap<>();

        for (Medicine medicine : medicines) {

            validateMedicineIsActiveAndIsExpired(medicine);

            medicineMap.put(
                    medicine.getMedicineId(),
                    medicine
            );

        }

        for (CartItemRequest item : cartItems) {

            Medicine medicine = medicineMap.get(item.medicineId());

            if (medicine.getStockQuantity() < item.quantity()) {

                throw new BadRequestException(
                        medicine.getMedicineName()
                                + " has only "
                                + medicine.getStockQuantity()
                                + " units available."
                );

            }

        }

        return medicineMap;
    }

    public void validateMedicineIsActiveAndIsExpired(Medicine medicine) {

        if (medicine.getStatus() != MedicineStatus.ACTIVE) {

            throw new BadRequestException(medicine.getMedicineName() + " is inactive.");
        }

        if (!medicine.getExpiryDate().isAfter(LocalDate.now())) {

            throw new BadRequestException(medicine.getMedicineName() + " has expired.");
        }
    }

}
