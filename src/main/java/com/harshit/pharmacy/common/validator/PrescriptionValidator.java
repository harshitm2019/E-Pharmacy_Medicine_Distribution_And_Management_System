package com.harshit.pharmacy.common.validator;

import com.harshit.pharmacy.exception.BadRequestException;
import com.harshit.pharmacy.exception.ResourceNotFoundException;
import com.harshit.pharmacy.medicine.entity.Medicine;
import com.harshit.pharmacy.medicine.enums.PrescriptionNeed;
import com.harshit.pharmacy.order.dto.CheckoutRequest;
import com.harshit.pharmacy.prescription.entity.Prescription;
import com.harshit.pharmacy.prescription.repository.PrescriptionRepository;
import com.harshit.pharmacy.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PrescriptionValidator {

    private final PrescriptionRepository prescriptionRepository;

    public Prescription validatePrescription(CheckoutRequest request, User user, Map<Integer, Medicine> medicines) {


        Integer prescriptionId = request.prescriptionId();

        boolean prescriptionRequired = medicines.values().stream()
                .anyMatch(med -> med.getPrescriptionNeed() == PrescriptionNeed.YES);

        if (prescriptionId == null) {

            if (prescriptionRequired)
                throw new BadRequestException("Prescription is required for one or more medicines.");

            return null;

        }

        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Prescription not found."
                ));


        if (!prescription.getUser().getUserId().equals(user.getUserId())) {
            throw new BadRequestException("You are not allowed to use this prescription.");
        }


         return prescription;

    }

}
