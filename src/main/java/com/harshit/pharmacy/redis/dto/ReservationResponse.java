package com.harshit.pharmacy.redis.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ReservationResponse(

        boolean success,

        String reservationId,

        Integer medicineId,

        String medicineName,

        String message

) {
}