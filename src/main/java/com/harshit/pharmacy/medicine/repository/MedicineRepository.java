package com.harshit.pharmacy.medicine.repository;

import com.harshit.pharmacy.medicine.entity.Medicine;
import com.harshit.pharmacy.medicine.enums.MedicineStatus;
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


}
