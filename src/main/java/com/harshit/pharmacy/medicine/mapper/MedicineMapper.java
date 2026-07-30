package com.harshit.pharmacy.medicine.mapper;

import com.harshit.pharmacy.category.entity.Category;
import com.harshit.pharmacy.medicine.entity.Medicine;
import com.harshit.pharmacy.medicine.enums.MedicineStatus;
import com.harshit.pharmacy.medicine.enums.PrescriptionNeed;
import com.harshit.pharmacy.medicine.dto.MedicineRequest;
import com.harshit.pharmacy.medicine.dto.MedicineResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MedicineMapper {

    private MedicineMapper() {}

    public static Medicine toEntity(MedicineRequest request, Category category) {

        return Medicine.builder()
                .medicineName(request.medicineName().trim())
                .category(category)
                .manufacturer(request.manufacturer().trim())
                .manufactureDate(request.manufactureDate())
                .expiryDate(request.expiryDate())
                .batchNumber(request.batchNumber().trim())
                .price(request.price())
                .discount(request.discount())
                .stockQuantity(request.stockQuantity())
                .description(request.description().trim())
                .prescriptionNeed(PrescriptionNeed.valueOf(request.prescriptionNeed()))
                .medicineImage(request.medicineImage())
                .status(MedicineStatus.ACTIVE)
                .build();
    }

    public static void updateEntity(Medicine medicine, MedicineRequest request, Category category) {

        medicine.setMedicineName(request.medicineName().trim());
        medicine.setCategory(category);
        medicine.setManufacturer(request.manufacturer().trim());
        medicine.setManufactureDate(request.manufactureDate());
        medicine.setExpiryDate(request.expiryDate());
        medicine.setBatchNumber(request.batchNumber().trim());
        medicine.setPrice(request.price());
        medicine.setDiscount(request.discount());
        medicine.setStockQuantity(request.stockQuantity());
        medicine.setDescription(request.description().trim());
        medicine.setPrescriptionNeed(PrescriptionNeed.valueOf(request.prescriptionNeed()));
        medicine.setMedicineImage(request.medicineImage());
    }

    public static MedicineResponse toResponse(Medicine medicine) {

        return new MedicineResponse(
                medicine.getMedicineId(),
                medicine.getMedicineName(),
                medicine.getCategory().getCategoryId(),
                medicine.getCategory().getCategoryName(),
                medicine.getManufacturer(),
                medicine.getManufactureDate(),
                medicine.getExpiryDate(),
                medicine.getBatchNumber(),
                medicine.getPrice(),
                medicine.getDiscount(),
                calculateSellingPrice(
                        medicine.getPrice(),
                        medicine.getDiscount()
                ),
                medicine.getStockQuantity(),
                medicine.getDescription(),
                medicine.getPrescriptionNeed(),
                medicine.getStatus(),
                medicine.getMedicineImage(),
                medicine.getCreatedAt(),
                medicine.getUpdatedAt()

        );
    }

    private static BigDecimal calculateSellingPrice(BigDecimal price, BigDecimal discount) {

        if (price == null || discount == null)
            return BigDecimal.ZERO;

        return price.subtract(price.multiply(discount)
                        .divide(BigDecimal.valueOf(100),
                                2,
                                RoundingMode.HALF_UP
                            )

        );
    }
}
