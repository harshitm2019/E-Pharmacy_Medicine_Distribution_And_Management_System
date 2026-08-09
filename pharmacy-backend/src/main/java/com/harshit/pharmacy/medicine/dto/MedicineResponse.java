package com.harshit.pharmacy.medicine.dto;

import com.harshit.pharmacy.medicine.enums.MedicineStatus;
import com.harshit.pharmacy.medicine.enums.PrescriptionNeed;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record MedicineResponse(

        Integer medicineId,

        String medicineName,

        Integer categoryId,

        String categoryName,

        String manufacturer,

        LocalDate manufactureDate,

        LocalDate expiryDate,

        String batchNumber,

        BigDecimal price,

        BigDecimal discount,

        BigDecimal sellingPrice,

        Integer stockQuantity,

        String description,

        PrescriptionNeed prescriptionNeed,

        MedicineStatus status,

        String medicineImage,

        LocalDateTime createdAt,

        LocalDateTime updatedAt


) {
}
