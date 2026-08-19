package com.harshit.pharmacy.delivery.controller;

import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.response.ApiResponse;
import com.harshit.pharmacy.delivery.dto.AssignDeliveryBoyRequest;
import com.harshit.pharmacy.delivery.dto.CreateDeliveryBoyRequest;
import com.harshit.pharmacy.delivery.dto.DeliveryBoyResponse;
import com.harshit.pharmacy.delivery.dto.DeliveryStatusResponse;
import com.harshit.pharmacy.delivery.dto.UpdateDeliveryBoyRequest;
import com.harshit.pharmacy.delivery.service.DeliveryService;
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
@RequestMapping("/api/v1/admin/delivery")
@RequiredArgsConstructor
public class AdminDeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping("/delivery-boys")
    public ResponseEntity<ApiResponse<DeliveryBoyResponse>> createDeliveryBoy(
            @Valid @RequestBody CreateDeliveryBoyRequest request) {

        DeliveryBoyResponse response = deliveryService.createDeliveryBoy(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(SuccessMessages.DELIVERY_BOY_CREATED, response)
        );
    }

    @PutMapping("/delivery-boys/{deliveryBoyId}")
    public ResponseEntity<ApiResponse<DeliveryBoyResponse>> updateDeliveryBoy(
            @PathVariable Integer deliveryBoyId,
            @Valid @RequestBody UpdateDeliveryBoyRequest request) {

        DeliveryBoyResponse response = deliveryService.updateDeliveryBoy(deliveryBoyId, request);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.DELIVERY_BOY_UPDATED, response)
        );
    }

    @GetMapping("/delivery-boys")
    public ResponseEntity<ApiResponse<Page<DeliveryBoyResponse>>> getAllDeliveryBoys(
            @ParameterObject
            @PageableDefault(sort = "deliveryBoyId") Pageable pageable) {

        Page<DeliveryBoyResponse> response = deliveryService.getAllDeliveryBoys(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.DELIVERY_BOYS_FETCHED, response)
        );
    }

    @GetMapping("/delivery-boys/available")
    public ResponseEntity<ApiResponse<Page<DeliveryBoyResponse>>> getAvailableDeliveryBoys(
            @ParameterObject
            @PageableDefault(sort = "deliveryBoyId") Pageable pageable) {

        Page<DeliveryBoyResponse> response = deliveryService.getAvailableDeliveryBoys(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.AVAILABLE_DELIVERY_BOYS_FETCHED, response)
        );
    }

    @PostMapping("/assign")
    public ResponseEntity<ApiResponse<DeliveryStatusResponse>> assignDeliveryBoy(
            @Valid @RequestBody AssignDeliveryBoyRequest request) {

        DeliveryStatusResponse response = deliveryService.assignDeliveryBoy(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(SuccessMessages.DELIVERY_BOY_ASSIGNED, response)
        );
    }
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<DeliveryStatusResponse>> getDeliveryStatus(
            @PathVariable Integer orderId) {

        DeliveryStatusResponse response = deliveryService.getDeliveryStatus(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.DELIVERY_STATUS_FETCHED, response));
    }

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<Page<DeliveryStatusResponse>>> getDeliveryStatusByStatus(
            @RequestParam String status,
            @ParameterObject @PageableDefault(sort = "assignedDate") Pageable pageable) {

        Page<DeliveryStatusResponse> response = deliveryService.getDeliveryStatusByStatus(status, pageable);

        return ResponseEntity.ok(
                ApiResponse.success(SuccessMessages.DELIVERY_STATUS_FETCHED, response)
        );
    }

}