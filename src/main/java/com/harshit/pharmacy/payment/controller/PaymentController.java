package com.harshit.pharmacy.payment.controller;

import com.harshit.pharmacy.common.response.ApiResponse;
import com.harshit.pharmacy.payment.dto.PaymentResponse;
import com.harshit.pharmacy.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getMyPayments() {

       List<PaymentResponse> response = paymentService.getMyPayments();

       return ResponseEntity.status(HttpStatus.OK).body(

               ApiResponse.success("Payment List Fetched Successfully", response)
       );
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPayment(@PathVariable Integer paymentId) {


          PaymentResponse paymentResponse = paymentService.getPayment(paymentId);

          return ResponseEntity.status(HttpStatus.OK).body(

                    ApiResponse.success("Payment Fetched Successfully", paymentResponse)
          );


    }
}