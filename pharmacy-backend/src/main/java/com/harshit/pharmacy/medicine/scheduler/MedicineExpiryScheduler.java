package com.harshit.pharmacy.medicine.scheduler;


import com.harshit.pharmacy.email.service.EmailService;
import com.harshit.pharmacy.medicine.entity.Medicine;
import com.harshit.pharmacy.medicine.enums.MedicineStatus;
import com.harshit.pharmacy.medicine.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MedicineExpiryScheduler {

    private final MedicineRepository medicineRepository;
    private final EmailService emailService;

    @Scheduled(cron = "0 0 5 * * *")
    @Transactional
    public void notifyExpiringMedicines() {

        LocalDate today = LocalDate.now();

        List<Medicine> medicines = medicineRepository.findByStatusAndExpiryDateBetweenOrderByExpiryDateAsc(
                MedicineStatus.ACTIVE,
                today,
                today.plusDays(10)
        );

        if (medicines.isEmpty()) {
            return;
        }

        emailService.sendMedicineExpiryEmail(medicines);

        medicines.forEach(medicine -> medicine.setStatus(MedicineStatus.INACTIVE));

        medicineRepository.saveAll(medicines);

    }
}
