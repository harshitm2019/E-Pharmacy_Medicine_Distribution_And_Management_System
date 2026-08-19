package com.harshit.pharmacy.payment.controller;

import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.response.ApiResponse;
import com.harshit.pharmacy.exception.ResourceNotFoundException;
import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.order.repository.OrderRepository;
import com.harshit.pharmacy.order.service.OrderService;
import com.harshit.pharmacy.payment.dto.PaymentRequest;
import com.harshit.pharmacy.payment.dto.PaymentResponse;
import com.harshit.pharmacy.payment.enums.PaymentStatus;
import com.harshit.pharmacy.payment.service.PaymentService;
import com.harshit.pharmacy.prescription.enums.PrescriptionStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @PostMapping("/online")
    public ResponseEntity<ApiResponse<PaymentResponse>> processOnlinePayment(
            @Valid @RequestBody PaymentRequest request) {

        PaymentResponse response = paymentService.processOnlinePayment(request);

        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));

        if (response.paymentStatus().equals(PaymentStatus.SUCCESS.name()) &&
                (order.getPrescription() == null || order.getPrescription().getStatus() == PrescriptionStatus.APPROVED)) {

            orderService.confirmOrder(request.orderId());
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.PAYMENT_PROCESSED_SUCCESSFULLY, response)
        );
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getMyPayments() {

        List<PaymentResponse> response = paymentService.getMyPayments();

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.PAYMENTS_FETCHED_SUCCESSFULLY, response)
        );
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPayment(@PathVariable Integer paymentId) {

        PaymentResponse response = paymentService.getPayment(paymentId);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.PAYMENT_FETCHED_SUCCESSFULLY, response)
        );
    }
}