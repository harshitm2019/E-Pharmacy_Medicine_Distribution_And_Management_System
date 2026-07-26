package com.harshit.pharmacy.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PaymentRequest(

        @NotNull(message = "Order Id is Required")
        Integer orderId,

        @NotBlank(message = "Payment Method is Required")
        @Pattern(regexp = "^(COD|UPI|DEBIT_CARD|CREDIT_CARD)$",
                 message = "Payment method must be one of COD, UPI, DEBIT_CARD, or CREDIT_CARD.")
        String paymentMethod

) {
}
