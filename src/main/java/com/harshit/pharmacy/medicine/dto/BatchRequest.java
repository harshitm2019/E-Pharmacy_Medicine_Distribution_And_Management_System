package com.harshit.pharmacy.medicine.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;


public record BatchRequest(

        @NotNull(message = "Medicine is required.")
        Integer medicineId,

        @NotBlank(message = "Batch number is required.")
        @Size(max = 100)
        String batchNumber,

        @NotNull(message = "Manufacture date is required.")
        @PastOrPresent
        LocalDate manufactureDate,

        @NotNull(message = "Expiry date is required.")
        @Future
        LocalDate expiryDate,

        @NotNull(message = "Price is required.")
        @DecimalMin(value = "0.01")
        BigDecimal price,

        @NotNull(message = "Discount is required.")
        @DecimalMin(value = "0")
        @DecimalMax(value = "100")
        BigDecimal discount,

        @NotNull(message = "Stock quantity is required.")
        @Min(0)
        Integer stockQuantity

) {
}