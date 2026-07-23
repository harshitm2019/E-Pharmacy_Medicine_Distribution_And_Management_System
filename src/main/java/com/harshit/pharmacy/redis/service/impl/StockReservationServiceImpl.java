package com.harshit.pharmacy.redis.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harshit.pharmacy.common.constants.ErrorMessages;
import com.harshit.pharmacy.redis.constants.RedisConstants;
import com.harshit.pharmacy.redis.dto.ReservationItem;
import com.harshit.pharmacy.redis.dto.ReservationRequest;
import com.harshit.pharmacy.redis.dto.ReservationResponse;
import com.harshit.pharmacy.redis.mapper.RedisMapper;
import com.harshit.pharmacy.redis.service.StockReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockReservationServiceImpl implements StockReservationService {

    private final StringRedisTemplate redisTemplate;
    private final DefaultRedisScript<Long> reserveStockScript;
    private final DefaultRedisScript<Long> releaseStockScript;
    private final DefaultRedisScript<Long> confirmReservationScript;
    private final ObjectMapper objectMapper;
    private final RedisMapper redisMapper;


    @Override
    public ReservationResponse reserveStock(ReservationRequest request) {

        String reservationId = UUID.randomUUID().toString();

        ReservationRequest reservation = redisMapper.toReservationRequest(reservationId, request);

        List<String> keys = new ArrayList<>();
        List<String> args = new ArrayList<>();

        for (ReservationItem item : reservation.items()) {

            keys.add(RedisConstants.STOCK_KEY + item.medicineId());
            args.add(String.valueOf(item.quantity()));

        }

        keys.add(RedisConstants.RESERVATION_KEY + reservationId);

        try {

            String reservationJson = objectMapper.writeValueAsString(reservation);

            args.add(reservationId);
            args.add(reservationJson);

            Long result = redisTemplate.execute(reserveStockScript, keys, args);

            if (result == 1L)
                return redisMapper.toSuccessResponse(reservationId);

            long value = result;

            if (value < 0) {

                int index = (int) Math.abs(value) - 1;
                ReservationItem failedItem = reservation.items().get(index);
                return redisMapper.toFailureResponse(failedItem, "Medicine Stock Not Found");

            }

            int index = (int) value - 1;
            ReservationItem failedItem = reservation.items().get(index);
            return redisMapper.toFailureResponse(failedItem, "Insufficient Stock");

        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Unable to serialize reservation.", e);
        }


    }

    @Override
    public void confirmReservation(String reservationId) {

        Long result = redisTemplate.execute(
                confirmReservationScript,
                List.of(RedisConstants.RESERVATION_KEY + reservationId),
                reservationId
        );

        if (result == 0L) {
            throw new IllegalStateException("Reservation not found.");
        }

    }

    @Override
    public void releaseReservation(String reservationId) {

        Long result = redisTemplate.execute(
                releaseStockScript,
                List.of(RedisConstants.RESERVATION_KEY + reservationId)
        );

        if (result == 0L) {
            throw new IllegalStateException("Reservation not found.");
        }

    }
}