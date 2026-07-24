package com.harshit.pharmacy.medicine.repository;

import com.harshit.pharmacy.medicine.dto.MedicineInventory;
import com.harshit.pharmacy.medicine.entity.MedicineBatch;
import com.harshit.pharmacy.medicine.enums.BatchStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MedicineBatchRepository extends JpaRepository<MedicineBatch, Integer> {

    boolean existsByBatchNumberIgnoreCase(String batchNumber);

    Optional<MedicineBatch> findByBatchNumberIgnoreCase(String batchNumber);

    Page<MedicineBatch> findByMedicineMedicineId(Integer medicineId, Pageable pageable);

    @Query("""
            SELECT COALESCE(SUM(b.stockQuantity), 0)
            FROM MedicineBatch b
            WHERE b.medicine.medicineId = :medicineId
            AND b.status = :status
            """)
    Integer getTotalStockByMedicineId(Integer medicineId, BatchStatus status);


    @Query("""
       SELECT MIN((b.price - (b.price * b.discount / 100)))
       FROM MedicineBatch b
       WHERE b.medicine.medicineId=:medicineId
       AND b.status=:status
       """)
    BigDecimal getMinimumSellingPrice(Integer medicineId, BatchStatus status);

    @Query("""
    SELECT
        COALESCE(SUM(b.stockQuantity),0) AS totalStock,
        MIN(b.price - (b.price * b.discount / 100)) AS sellingPrice
    FROM MedicineBatch b
    WHERE b.medicine.medicineId = :medicineId
      AND b.status = :status
    """)
    MedicineInventory getMedicineInventory(Integer medicineId, BatchStatus status);

    @Query("""
    SELECT
        b.medicine.medicineId AS medicineId,
        COALESCE(SUM(b.stockQuantity), 0) AS totalStock,
        MIN(b.price - (b.price * b.discount / 100)) AS sellingPrice
    FROM MedicineBatch b
    WHERE b.medicine.medicineId IN :medicineIds
      AND b.status = :status
    GROUP BY b.medicine.medicineId
    """)
    List<MedicineInventory> getMedicineInventories(
            List<Integer> medicineIds,
            BatchStatus status
    );

    List<MedicineBatch> findByMedicineMedicineIdAndStatusOrderByExpiryDateAsc(
            Integer medicineId,
            BatchStatus status
    );

    List<MedicineBatch> findByStatusAndExpiryDateLessThanEqual(BatchStatus batchStatus, LocalDate cutoffDate);
}