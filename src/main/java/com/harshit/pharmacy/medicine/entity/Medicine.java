package com.harshit.pharmacy.medicine.entity;


import com.harshit.pharmacy.category.entity.Category;
import com.harshit.pharmacy.medicine.enums.MedicineStatus;
import com.harshit.pharmacy.medicine.enums.PrescriptionNeed;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "medicine")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "med_id")
    private Integer medicineId;

    @Column(name = "med_name", nullable = false, length = 100)
    private String medicineName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id", nullable = false)
    private Category category;

    @Column(name = "manufacturer", nullable = false, length = 100)
    private String manufacturer;

    @Column(name = "manufacture", nullable = false)
    private LocalDate manufactureDate;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "batch_no", nullable = false, unique = true, length = 100)
    private String batchNumber;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "discount", nullable = false, precision = 5, scale = 2)
    private BigDecimal discount;

    @Column(name = "stock_qty", nullable = false)
    private Integer stockQuantity;

    @Column(nullable = false, length = 300)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "prescription_need", nullable = false)
    private PrescriptionNeed prescriptionNeed;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MedicineStatus status;

    @Column(name = "med_img")
    private String medicineImage;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedAt;


}
