package com.harshit.pharmacy.scheduler;

import com.harshit.pharmacy.medicine.service.MedicineBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchExpiryScheduler {

    private final MedicineBatchService medicineBatchService;

    @Scheduled(cron = "0 0 0 * * *")
    public void expireBatches() {
        medicineBatchService.expireBatches();
    }

}
