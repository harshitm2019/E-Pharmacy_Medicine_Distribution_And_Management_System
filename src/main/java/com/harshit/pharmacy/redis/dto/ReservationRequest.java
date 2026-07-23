package com.harshit.pharmacy.redis.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ReservationRequest(


        String reservationId,
        Integer userId,
        List<ReservationItem> items,
        LocalDateTime expiresAt,
        Long expiresAtEpoch


) {
}
