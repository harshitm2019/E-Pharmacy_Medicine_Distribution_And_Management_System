package com.harshit.pharmacy.medicine.dto;

import com.harshit.pharmacy.medicine.enums.BatchStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record BatchResponse(


        Integer batchId,

        Integer medicineId,

        String medicineName,

        String batchNumber,

        LocalDate manufactureDate,

        LocalDate expiryDate,

        BigDecimal price,

        BigDecimal discount,

        BigDecimal sellingPrice,

        Integer stockQuantity,

        BatchStatus status,

        LocalDateTime createdAt,

        LocalDateTime updatedAt



) {
}
