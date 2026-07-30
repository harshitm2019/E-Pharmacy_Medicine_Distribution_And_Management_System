package com.harshit.pharmacy.medicine.repository;

import com.harshit.pharmacy.medicine.entity.Medicine;
import com.harshit.pharmacy.medicine.enums.MedicineStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer> {


    boolean existsByBatchNumberIgnoreCase(String batchNumber);

    Optional<Medicine> findByBatchNumberIgnoreCase(String batchNumber);

    Page<Medicine> findByStatus(MedicineStatus status, Pageable pageable);

    Page<Medicine> findByMedicineNameContainingIgnoreCaseAndStatus(String keyword, MedicineStatus status, Pageable pageable);

    boolean existsByCategoryCategoryId(Integer categoryId);

    Page<Medicine> findByMedicineNameContainingIgnoreCase(String keyword, Pageable pageable);

    List<Medicine> findByMedicineIdIn(Collection<Integer> medicineIds);

    Page<Medicine> findByStatusAndCategoryCategoryId(
            MedicineStatus status,
            Integer categoryId,
            Pageable pageable
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
          SELECT m
          FROM Medicine m
          WHERE m.medicineId IN :medicineIds
          """)
    List<Medicine> findAllForUpdate(@Param("medicineIds") Collection<Integer> medicineIds);

    boolean existsByMedicineNameIgnoreCaseAndStatus(
            String medicineName,
            MedicineStatus status
    );

    boolean existsByMedicineNameIgnoreCaseAndStatusAndMedicineIdNot(
            String medicineName,
            MedicineStatus status,
            Integer medicineId
    );

    List<Medicine> findByStatusAndExpiryDateBetweenOrderByExpiryDateAsc(
            MedicineStatus status,
            LocalDate today,
            LocalDate reminderDate
    );

}