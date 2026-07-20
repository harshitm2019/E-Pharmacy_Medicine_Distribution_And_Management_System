package com.harshit.pharmacy.prescription.repository;

import com.harshit.pharmacy.prescription.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {




}
