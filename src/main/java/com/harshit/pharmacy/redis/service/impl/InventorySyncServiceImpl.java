package com.harshit.pharmacy.redis.service.impl;

import com.harshit.pharmacy.medicine.enums.BatchStatus;
import com.harshit.pharmacy.medicine.repository.MedicineBatchRepository;
import com.harshit.pharmacy.redis.constants.RedisConstants;
import com.harshit.pharmacy.redis.service.InventorySyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
public class InventorySyncServiceImpl implements InventorySyncService {

    private final MedicineBatchRepository medicineBatchRepository;
    private final StringRedisTemplate redisTemplate;

    @Override
    public void syncMedicineStock(Integer medicineId) {

        Integer totalStock = medicineBatchRepository.getTotalStockByMedicineId(
                medicineId,
                BatchStatus.ACTIVE
        );

        totalStock = totalStock == null ? 0 : totalStock;

        Integer finalStock = totalStock;

        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(
                    new TransactionSynchronization() {
                        @Override
                        public void afterCommit() {
                            redisTemplate.opsForValue().set(
                                    RedisConstants.STOCK_KEY + medicineId,
                                    String.valueOf(finalStock)
                            );
                        }
                    }
            );
        } else {
            redisTemplate.opsForValue().set(
                    RedisConstants.STOCK_KEY + medicineId,
                    String.valueOf(finalStock)
            );
        }

    }
}
