package com.harshit.pharmacy.medicine.mapper;

import com.harshit.pharmacy.category.entity.Category;
import com.harshit.pharmacy.medicine.dto.MedicineRequest;
import com.harshit.pharmacy.medicine.dto.MedicineResponse;
import com.harshit.pharmacy.medicine.entity.Medicine;
import com.harshit.pharmacy.medicine.enums.MedicineStatus;
import com.harshit.pharmacy.medicine.enums.PrescriptionNeed;

import java.math.BigDecimal;

public final class MedicineMapper {

    private MedicineMapper() {
    }

    public static Medicine toEntity(MedicineRequest request, Category category) {

        return Medicine.builder()
                .medicineName(request.medicineName().trim())
                .category(category)
                .manufacturer(request.manufacturer().trim())
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
        medicine.setDescription(request.description().trim());
        medicine.setPrescriptionNeed(PrescriptionNeed.valueOf(request.prescriptionNeed()));
        medicine.setMedicineImage(request.medicineImage());

    }

    public static MedicineResponse toResponse(Medicine medicine, Integer totalStock, BigDecimal sellingPrice, boolean available) {

        return MedicineResponse.builder()
                .medicineId(medicine.getMedicineId())
                .medicineName(medicine.getMedicineName())
                .categoryId(medicine.getCategory().getCategoryId())
                .categoryName(medicine.getCategory().getCategoryName())
                .manufacturer(medicine.getManufacturer())
                .description(medicine.getDescription())
                .prescriptionNeed(medicine.getPrescriptionNeed())
                .status(medicine.getStatus())
                .medicineImage(medicine.getMedicineImage())
                .totalStock(totalStock)
                .sellingPrice(sellingPrice)
                .available(available)
                .createdAt(medicine.getCreatedAt())
                .updatedAt(medicine.getUpdatedAt())
                .build();
    }

}