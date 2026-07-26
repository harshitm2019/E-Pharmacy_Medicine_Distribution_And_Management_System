package com.harshit.pharmacy.order.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateOrderRequest(


        @Size(max = 500, message = "Shipping address cannot exceed 500 characters.")
        String shippingAddress,

        @Pattern(
                regexp = "COD|UPI|DEBIT_CARD|CREDIT_CARD",
                message = "Payment method must be one of: COD, UPI, DEBIT_CARD or CREDIT_CARD."
        )
        String paymentMethod

) {
}
