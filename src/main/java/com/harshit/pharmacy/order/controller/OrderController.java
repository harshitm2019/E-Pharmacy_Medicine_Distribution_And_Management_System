package com.harshit.pharmacy.order.controller;


import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.response.ApiResponse;
import com.harshit.pharmacy.order.dto.CheckoutRequest;
import com.harshit.pharmacy.order.dto.CheckoutResponse;
import com.harshit.pharmacy.order.dto.OrderResponse;
import com.harshit.pharmacy.order.dto.UpdateOrderRequest;
import com.harshit.pharmacy.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<CheckoutResponse>> checkout(
            @Valid @RequestBody CheckoutRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(

                ApiResponse.success(SuccessMessages.ORDER_PLACED_SUCCESSFULLY, orderService.checkout(request))
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getMyOrders() {

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.ORDERS_FETCHED_SUCCESSFULLY, orderService.getMyOrders())
        );
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(
            @PathVariable Integer orderId) {

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.ORDER_FETCHED_SUCCESSFULLY, orderService.getOrderById(orderId))
        );
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(
            @PathVariable Integer orderId) {

        orderService.cancelOrder(orderId);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.ORDER_CANCELLED_SUCCESSFULLY, null));
    }

    @PatchMapping(value = "/{orderId}/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Void>> updateOrder(
            @PathVariable Integer orderId,
            @Valid
            @RequestPart("request")
            UpdateOrderRequest request,
            @RequestPart(value = "prescription", required = false)
            MultipartFile prescription) {

        orderService.updateOrder(orderId, request, prescription);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.ORDER_UPDATED_SUCCESSFULLY, null));
    }

}
