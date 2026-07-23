package com.harshit.pharmacy.redis.dto;

import lombok.Builder;

@Builder
public record ReservationItem(

        Integer medicineId,

        String medicineName,

        Integer quantity

) {
}
