package com.harshit.pharmacy.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PaymentRequest(

        @NotNull(message = "Order ID is required.")
        Integer orderId,

        @NotBlank(message = "Payment method is required.")
        @Pattern(
                regexp = "^(UPI|CARD|NET_BANKING)$",
                message = "Payment method must be one of: UPI, CARD, NET_BANKING"
        )
        String paymentMethod,

        @NotBlank(message = "Reservation id is required")
        String reservationId

) {
}