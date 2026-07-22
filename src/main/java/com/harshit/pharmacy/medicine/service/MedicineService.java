package com.harshit.pharmacy.medicine.service;

import com.harshit.pharmacy.medicine.dto.MedicineRequest;
import com.harshit.pharmacy.medicine.dto.MedicineResponse;
import com.harshit.pharmacy.medicine.dto.MedicineStatusRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MedicineService {


    MedicineResponse createMedicine(MedicineRequest request);

    MedicineResponse updateMedicine(Integer medicineId, MedicineRequest request);

    MedicineResponse getMedicineById(Integer medicineId);

    Page<MedicineResponse> getAllActiveMedicines(Pageable pageable);

    Page<MedicineResponse> getAllMedicines(Pageable pageable);

    List<MedicineResponse> updateStatus(MedicineStatusRequest request);

    MedicineResponse getActiveMedicineById(Integer medicineId);

    Page<MedicineResponse> searchActiveMedicines(String keyword, Pageable pageable);

    Page<MedicineResponse> searchMedicines(String keyword, Pageable pageable);
}
