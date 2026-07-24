package com.harshit.pharmacy.medicine.mapper;

import com.harshit.pharmacy.medicine.dto.BatchRequest;
import com.harshit.pharmacy.medicine.dto.BatchResponse;
import com.harshit.pharmacy.medicine.entity.Medicine;
import com.harshit.pharmacy.medicine.entity.MedicineBatch;
import com.harshit.pharmacy.medicine.enums.BatchStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MedicineBatchMapper {

    private MedicineBatchMapper() {
    }

    public static MedicineBatch toEntity(
            BatchRequest request,
            Medicine medicine
    ) {

        return MedicineBatch.builder()
                .medicine(medicine)
                .batchNumber(request.batchNumber().trim())
                .manufactureDate(request.manufactureDate())
                .expiryDate(request.expiryDate())
                .price(request.price())
                .discount(request.discount())
                .stockQuantity(request.stockQuantity())
                .status(BatchStatus.ACTIVE)
                .build();

    }

    public static void updateEntity(MedicineBatch batch, BatchRequest request) {

        batch.setBatchNumber(request.batchNumber().trim());
        batch.setManufactureDate(request.manufactureDate());
        batch.setExpiryDate(request.expiryDate());
        batch.setPrice(request.price());
        batch.setDiscount(request.discount());
        batch.setStockQuantity(request.stockQuantity());

    }

    public static BatchResponse toResponse(MedicineBatch batch) {

        return BatchResponse.builder()
                .batchId(batch.getBatchId())
                .medicineId(batch.getMedicine().getMedicineId())
                .medicineName(batch.getMedicine().getMedicineName())
                .batchNumber(batch.getBatchNumber())
                .manufactureDate(batch.getManufactureDate())
                .expiryDate(batch.getExpiryDate())
                .price(batch.getPrice())
                .discount(batch.getDiscount())
                .sellingPrice(
                        calculateSellingPrice(
                                batch.getPrice(),
                                batch.getDiscount()
                        )
                )
                .stockQuantity(batch.getStockQuantity())
                .status(batch.getStatus())
                .createdAt(batch.getCreatedAt())
                .updatedAt(batch.getUpdatedAt())
                .build();

    }

    private static BigDecimal calculateSellingPrice(
            BigDecimal price,
            BigDecimal discount
    ) {

        if (price == null || discount == null) {
            return BigDecimal.ZERO;
        }

        return price.subtract(
                price.multiply(discount)
                        .divide(BigDecimal.valueOf(100), 2,
                                RoundingMode.HALF_UP
                        )
        );

    }

}