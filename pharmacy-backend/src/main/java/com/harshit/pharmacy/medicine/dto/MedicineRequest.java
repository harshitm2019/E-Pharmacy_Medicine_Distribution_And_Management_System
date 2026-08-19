package com.harshit.pharmacy.medicine.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MedicineRequest(

        @NotBlank(message = "Medicine name is required.")
        @Size(
                min = 3,
                max = 100,
                message = "Medicine name must be between 3 and 100 characters."
        )
        String medicineName,

        @NotNull(message = "Category is required.")
        Integer categoryId,

        @NotBlank(message = "Manufacturer is required.")
        @Size(max = 100, message = "Manufacturer must not exceed 100 characters.")
        String manufacturer,

        @NotNull(message = "Manufacture date is required.")
        @PastOrPresent(message = "Manufacture date cannot be in the future.")
        LocalDate manufactureDate,

        @NotNull(message = "Expiry date is required.")
        @Future(message = "Expiry date must be in the future.")
        LocalDate expiryDate,

        @NotBlank(message = "Batch number is required.")
        @Size(max = 100, message = "Batch number must not exceed 100 characters.")
        String batchNumber,

        @NotNull(message = "Price is required.")
        @DecimalMin(value = "0.01", message = "Price must be greater than 0.")
        BigDecimal price,

        @NotNull(message = "Discount is required.")
        @DecimalMin(value = "0.00", message = "Discount cannot be negative.")
        @DecimalMax(value = "100.0", message = "Discount cannot exceed 100%.")
        BigDecimal discount,

        @NotNull(message = "Stock quantity is required.")
        @Min(value = 0, message = "Stock quantity cannot be negative.")
        Integer stockQuantity,

        @NotBlank(message = "Description is required.")
        @Size(max = 300, message = "Description must not exceed 300 characters.")
        String description,

        @NotBlank(message = "Prescription requirement is required.")
        @Pattern(regexp = "^(YES|NO)$", message = "Prescription requirement must be YES or NO.")
        String prescriptionNeed,

        String medicineImage

) {
}