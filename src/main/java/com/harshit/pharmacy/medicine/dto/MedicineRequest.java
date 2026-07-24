package com.harshit.pharmacy.medicine.dto;

import com.harshit.pharmacy.medicine.constants.MedicineConstants;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MedicineRequest(

        @NotBlank(message = "Medicine name is required.")
        @Size(
                min = MedicineConstants.MEDICINE_NAME_MIN_LENGTH,
                max = MedicineConstants.MEDICINE_NAME_MAX_LENGTH,
                message = "Medicine name must be between 3 and 100 characters."
        )
        String medicineName,

        @NotNull(message = "Category is required.")
        Integer categoryId,

        @NotBlank(message = "Manufacturer is required.")
        @Size(
                max = MedicineConstants.MANUFACTURER_MAX_LENGTH,
                message = "Manufacturer must not exceed 100 characters."
        )
        String manufacturer,

        @NotBlank(message = "Description is required.")
        @Size(
                max = MedicineConstants.DESCRIPTION_MAX_LENGTH,
                message = "Description must not exceed 300 characters."
        )
        String description,

        @NotBlank(message = "Prescription requirement is required.")
        @Pattern(
                regexp = MedicineConstants.PRESCRIPTION_NEED_PATTERN,
                message = "Prescription requirement must be YES or NO."
        )
        String prescriptionNeed,

        String medicineImage
) {
}