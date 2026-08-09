package com.harshit.pharmacy.email.service;

import com.harshit.pharmacy.medicine.entity.Medicine;

import java.util.List;

public interface EmailService {

    void sendMedicineExpiryEmail(List<Medicine> medicines);

}
