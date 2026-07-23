package com.harshit.pharmacy.scheduler;

import com.harshit.pharmacy.redis.constants.RedisConstants;
import com.harshit.pharmacy.redis.service.StockReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationCleanupScheduler {

    private final StringRedisTemplate redisTemplate;

    private final StockReservationService stockReservationService;

    @Scheduled(fixedDelay = 60000)
    public void cleanupExpiredReservations() {

        long now = Instant.now().toEpochMilli();

        Set<String> reservationIds = redisTemplate.opsForZSet().rangeByScore(RedisConstants.ACTIVE_RESERVATIONS_KEY, 0, now);

        if (reservationIds == null || reservationIds.isEmpty())
            return;

        for (String reservationId : reservationIds) {

            try {

                stockReservationService.releaseReservation(reservationId);

                log.info("Expired reservation released : {}", reservationId);

            } catch (Exception e) {

                log.error("Unable to release reservation : {}", reservationId, e);

            }
        }



    }

}