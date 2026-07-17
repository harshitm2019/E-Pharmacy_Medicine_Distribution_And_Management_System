package com.harshit.pharmacy.medicine.repository;

import com.harshit.pharmacy.medicine.entity.Medicine;
import com.harshit.pharmacy.medicine.enums.MedicineStatus;
import com.harshit.pharmacy.medicine.record.MedicineResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer> {


    boolean existsByBatchNumberIgnoreCase(String batchNumber);

    Optional<Medicine> findByBatchNumberIgnoreCase(String batchNumber);

    Page<Medicine> findByStatus(MedicineStatus status, Pageable pageable);

    Page<Medicine> findByMedicineNameContainingIgnoreCaseAndStatus(String keyword, MedicineStatus status, Pageable pageable);

    boolean existsByCategoryCategoryId(Integer categoryId);

    Optional<Medicine> findByMedicineIdAndStatus(Integer medicineId, MedicineStatus status);

    Page<Medicine> findByMedicineNameContainingIgnoreCase(String keyword, Pageable pageable);
}
