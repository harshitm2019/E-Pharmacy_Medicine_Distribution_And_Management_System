package com.harshit.pharmacy.medicine.service;

import com.harshit.pharmacy.medicine.record.MedicineRequest;
import com.harshit.pharmacy.medicine.record.MedicineResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MedicineService {


    MedicineResponse createMedicine(MedicineRequest request);

    MedicineResponse updateMedicine(Integer medicineId, MedicineRequest request);


    MedicineResponse getMedicineById(Integer medicineId);

    Page<MedicineResponse> getAllMedicines(Pageable pageable);



}
