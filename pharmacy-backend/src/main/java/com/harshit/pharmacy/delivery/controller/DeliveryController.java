package com.harshit.pharmacy.delivery.controller;

import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.response.ApiResponse;
import com.harshit.pharmacy.delivery.dto.DeliveryStatusResponse;
import com.harshit.pharmacy.delivery.dto.UpdateDeliveryStatusRequest;
import com.harshit.pharmacy.delivery.service.DeliveryService;
import com.harshit.pharmacy.order.dto.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/my-orders")
    public ResponseEntity<ApiResponse<Page<DeliveryStatusResponse>>>getMyDeliveryOrdersByStatus(
            @RequestParam String status,
            @ParameterObject
            @PageableDefault(sort = "assignedDate")
            Pageable pageable) {

        Page<DeliveryStatusResponse> response = deliveryService.getMyDeliveryOrdersByStatus(status, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.DELIVERY_STATUS_FETCHED, response)
        );
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderDetails(@PathVariable Integer orderId) {

        OrderResponse response = deliveryService.getOrderDetails(orderId);

        return ResponseEntity.status(HttpStatus.OK).body(
                      ApiResponse.success(SuccessMessages.ORDER_FETCHED_SUCCESSFULLY, response)
        );
    }
}