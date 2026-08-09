package com.harshit.pharmacy.email.service.impl;

import com.harshit.pharmacy.email.service.EmailService;
import com.harshit.pharmacy.medicine.entity.Medicine;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${pharmacy.admin.email}")
    private String adminEmail;

    @Override
    public void sendMedicineExpiryEmail(List<Medicine> medicines) {

        if (medicines == null || medicines.isEmpty()) {
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(adminEmail);
        message.setSubject("Pharmacy Management System - Medicine Expiry Alert");

        StringBuilder body = new StringBuilder();

        body.append("Dear Administrator,\n\n");

        body.append("The following medicines have been marked INACTIVE ")
                .append("because they will expire within the next 10 days.\n\n");

        body.append("----------------------------------------------------\n");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        for (Medicine medicine : medicines) {

            body.append("Medicine : ")
                    .append(medicine.getMedicineName())
                    .append("\n");

            body.append("Batch No : ")
                    .append(medicine.getBatchNumber())
                    .append("\n");

            body.append("Expiry   : ")
                    .append(medicine.getExpiryDate().format(formatter))
                    .append("\n");

            body.append("----------------------------------------\n");
        }

        body.append("\nPlease create fresh batches for these medicines.\n\n");
        body.append("Regards,\n");
        body.append("Pharmacy Management System");

        message.setText(body.toString());

        mailSender.send(message);
    }
}