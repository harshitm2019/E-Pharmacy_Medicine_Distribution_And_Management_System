package com.harshit.pharmacy.order.controller;

import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.response.ApiResponse;
import com.harshit.pharmacy.common.swagger.annotations.order.*;
import com.harshit.pharmacy.common.swagger.constants.SwaggerConstants;
import com.harshit.pharmacy.order.dto.CheckoutRequest;
import com.harshit.pharmacy.order.dto.CheckoutResponse;
import com.harshit.pharmacy.order.dto.OrderResponse;
import com.harshit.pharmacy.order.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = SwaggerConstants.ORDER_TAG, description = "APIs for placing and managing orders")
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @CheckoutApi
    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<CheckoutResponse>> checkout(
            @Valid @RequestBody CheckoutRequest request) {

        CheckoutResponse response = orderService.checkout(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(SuccessMessages.ORDER_PLACED_SUCCESSFULLY, response)
        );
    }

    @GetMyOrdersApi
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getMyOrders() {

        List<OrderResponse> response = orderService.getMyOrders();

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.ORDERS_FETCHED_SUCCESSFULLY, response)
        );
    }

    @GetOrderByIdApi
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(
            @PathVariable Integer orderId) {

        OrderResponse response = orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.ORDER_FETCHED_SUCCESSFULLY, response)
        );
    }

    @CancelOrderApi
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(
            @PathVariable Integer orderId) {

        orderService.cancelOrder(orderId);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.ORDER_CANCELLED_SUCCESSFULLY)
        );
    }
}