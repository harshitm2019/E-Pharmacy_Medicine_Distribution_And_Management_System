package com.harshit.pharmacy.payment.controller;

import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.response.ApiResponse;
import com.harshit.pharmacy.common.swagger.annotations.payment.*;
import com.harshit.pharmacy.common.swagger.constants.SwaggerConstants;
import com.harshit.pharmacy.payment.dto.PaymentRequest;
import com.harshit.pharmacy.payment.dto.PaymentResponse;
import com.harshit.pharmacy.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = SwaggerConstants.PAYMENT_TAG, description = "APIs for processing and viewing payments")
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @ProcessPaymentApi
    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> processPayment(
            @Valid @RequestBody PaymentRequest request) {

        PaymentResponse response = paymentService.processPayment(request);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.PAYMENT_SUCCESSFUL, response)
        );
    }

    @GetMyPaymentsApi
    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getMyPayments() {

        List<PaymentResponse> response = paymentService.getMyPayments();

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.PAYMENTS_FETCHED_SUCCESSFULLY, response)
        );
    }

    @GetPaymentApi
    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPayment(
            @PathVariable Integer paymentId) {

        PaymentResponse response = paymentService.getPayment(paymentId);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.PAYMENT_FETCHED_SUCCESSFULLY, response)
        );
    }
}