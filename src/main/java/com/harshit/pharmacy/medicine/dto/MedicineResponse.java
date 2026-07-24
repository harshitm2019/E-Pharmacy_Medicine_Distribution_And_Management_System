package com.harshit.pharmacy.medicine.dto;

import com.harshit.pharmacy.medicine.enums.MedicineStatus;
import com.harshit.pharmacy.medicine.enums.PrescriptionNeed;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record MedicineResponse(

        Integer medicineId,

        String medicineName,

        Integer categoryId,

        String categoryName,

        String manufacturer,

        String description,

        PrescriptionNeed prescriptionNeed,

        MedicineStatus status,

        String medicineImage,

        Integer totalStock,

        BigDecimal sellingPrice,

        boolean available,

        LocalDateTime createdAt,

        LocalDateTime updatedAt



) {
}
