package com.harshit.pharmacy.order.dto;

import com.harshit.pharmacy.payment.enums.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record CheckoutRequest(


        @NotBlank(message = "Shipping address is required.")
        String shippingAddress,

        @NotNull(message = "Payment method is required.")
        @Pattern(regexp = "UPI|CARD|NET_BANKING|COD",
                message = "Payment method must be UPI, CARD, NET_BANKING or COD.")
        String paymentMethod,

        @NotEmpty(message = "Cart cannot be empty.")
        List<@Valid CartItemRequest> items


) {
}
