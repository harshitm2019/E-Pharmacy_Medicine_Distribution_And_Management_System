package com.harshit.pharmacy.redis.mapper;

import com.harshit.pharmacy.redis.constants.RedisConstants;
import com.harshit.pharmacy.redis.dto.ReservationItem;
import com.harshit.pharmacy.redis.dto.ReservationRequest;
import com.harshit.pharmacy.redis.dto.ReservationResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class RedisMapper {

     public ReservationRequest toReservationRequest(String reservationId,ReservationRequest request){


         return ReservationRequest.builder()
                 .reservationId(reservationId)
                 .userId(request.userId())
                 .items(request.items())
                 .expiresAt(LocalDateTime.now().plusMinutes(15))
                 .expiresAtEpoch(Instant.now().plusSeconds(900).toEpochMilli())
                 .build();

      }

    public ReservationResponse toSuccessResponse(String reservationId) {
        return ReservationResponse.builder()
                .success(true)
                .reservationId(reservationId)
                .message(RedisConstants.STOCK_RESERVED_MESSAGE)
                .build();
    }

    public ReservationResponse toFailureResponse(ReservationItem failedItem, String message) {
        return ReservationResponse.builder()
                .success(false)
                .medicineId(failedItem.medicineId())
                .medicineName(failedItem.medicineName())
                .message(message)
                .build();
    }


}
