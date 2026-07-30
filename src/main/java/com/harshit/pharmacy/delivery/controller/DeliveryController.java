package com.harshit.pharmacy.delivery.controller;

import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.response.ApiResponse;
import com.harshit.pharmacy.delivery.dto.DeliveryStatusResponse;
import com.harshit.pharmacy.delivery.dto.UpdateDeliveryStatusRequest;
import com.harshit.pharmacy.delivery.service.DeliveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PatchMapping("/orders/{orderId}/status")
    public ResponseEntity<ApiResponse<DeliveryStatusResponse>> processDeliveryUpdate(
            @PathVariable Integer orderId,
            @Valid @RequestBody UpdateDeliveryStatusRequest request) {

        DeliveryStatusResponse response = deliveryService.processDeliveryUpdate(orderId, request);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.DELIVERY_STATUS_UPDATED, response)
        );
    }

    @GetMapping("/orders/{orderId}/track")
    public ResponseEntity<ApiResponse<DeliveryStatusResponse>> trackDelivery(
            @PathVariable Integer orderId) {

        DeliveryStatusResponse response = deliveryService.trackDelivery(orderId);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.DELIVERY_STATUS_FETCHED, response)
        );
    }
}