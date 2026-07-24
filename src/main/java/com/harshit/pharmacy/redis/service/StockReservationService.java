package com.harshit.pharmacy.redis.service;

import com.harshit.pharmacy.redis.dto.ReservationRequest;
import com.harshit.pharmacy.redis.dto.ReservationResponse;

public interface StockReservationService {

    ReservationResponse reserveStock(ReservationRequest request);

    void confirmReservation(String reservationId);

    void releaseReservation(String reservationId);


}