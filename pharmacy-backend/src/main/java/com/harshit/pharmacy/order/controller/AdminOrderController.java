package com.harshit.pharmacy.order.controller;


import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.response.ApiResponse;
import com.harshit.pharmacy.order.dto.OrderResponse;
import com.harshit.pharmacy.order.dto.OrderStatusUpdateRequest;
import com.harshit.pharmacy.order.service.AdminOrderService;
import com.harshit.pharmacy.prescription.dto.PrescriptionDecisionRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/orders")
public class AdminOrderController {

    private final AdminOrderService orderService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<OrderResponse>>> getOrders(
            @RequestParam(required = false) String status,
            @ParameterObject
            @PageableDefault(sort = "orderDate") Pageable pageable) {

        Page<OrderResponse> response = orderService.getOrders(status, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.ORDERS_FETCHED_SUCCESSFULLY, response)
        );
    }

    @PatchMapping("/{orderId}/prescription")
    public ResponseEntity<ApiResponse<Void>> updatePrescriptionStatus(@PathVariable Integer orderId,
            @Valid @RequestBody PrescriptionDecisionRequest request) {

        orderService.updatePrescriptionStatus(orderId, request.prescriptionStatus());

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.PRESCRIPTION_STATUS_UPDATED_SUCCESSFULLY, null)
        );
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<Void>> changeOrderStatus(
            @PathVariable Integer orderId,
            @Valid @RequestBody OrderStatusUpdateRequest request) {

        orderService.changeOrderStatus(orderId, request.orderStatus());

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.ORDER_STATUS_UPDATED_SUCCESSFULLY, null)
        );
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(
            @PathVariable Integer orderId) {

        orderService.cancelOrder(orderId);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.ORDER_CANCELLED_SUCCESSFULLY, null)
        );
    }

}
