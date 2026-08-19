package com.harshit.pharmacy.prescription.entity;

import com.harshit.pharmacy.prescription.enums.PrescriptionStatus;
import com.harshit.pharmacy.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "prescription")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prescription {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "presc_id")
    private Integer prescriptionId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "file_path", nullable = false, length = 200)
    private String filePath;

    @Column(name = "doctor_name", nullable = false, length = 200)
    private String doctorName;

    @CreationTimestamp
    @Column(name = "uploaded_date", nullable = false,updatable = false)
    private LocalDateTime uploadedDate;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status", nullable = false,columnDefinition = "prescription_status_enum")
    private PrescriptionStatus status;


}
