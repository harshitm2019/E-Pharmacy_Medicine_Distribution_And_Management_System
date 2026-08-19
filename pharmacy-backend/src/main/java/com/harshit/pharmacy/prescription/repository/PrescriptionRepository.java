package com.harshit.pharmacy.prescription.repository;

import com.harshit.pharmacy.payment.entity.Payment;
import com.harshit.pharmacy.prescription.entity.Prescription;
import com.harshit.pharmacy.prescription.enums.PrescriptionStatus;
import com.harshit.pharmacy.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {


    Page<Prescription> findByUser(User user, Pageable pageable);

    Optional<Prescription> findByPrescriptionIdAndUser(
            Integer prescriptionId,
            User user
    );

    long countByStatus(PrescriptionStatus status);

    long countByUser(User user);

}
