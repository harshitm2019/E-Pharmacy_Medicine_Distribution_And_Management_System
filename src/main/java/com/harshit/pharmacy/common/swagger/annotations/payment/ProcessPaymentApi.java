package com.harshit.pharmacy.common.swagger.annotations.payment;


import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.swagger.constants.SwaggerConstants;
import com.harshit.pharmacy.common.swagger.constants.SwaggerResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Process Payment",
        description = "Processes payment for an order using the selected payment method."
)
@ApiResponses({
        @ApiResponse(responseCode = SwaggerResponses.OK, description = SuccessMessages.PAYMENT_SUCCESSFUL),
        @ApiResponse(responseCode = SwaggerResponses.BAD_REQUEST, description = SwaggerResponses.VALIDATION_FAILED),
        @ApiResponse(responseCode = SwaggerResponses.UNAUTHORIZED, description = SwaggerResponses.UNAUTHORIZED_DESC),
        @ApiResponse(responseCode = SwaggerResponses.NOT_FOUND, description = "Order not found"),
        @ApiResponse(responseCode = SwaggerResponses.CONFLICT, description = "Payment already processed for this order")
})
@SecurityRequirement(name = SwaggerConstants.BEARER_AUTH)
public @interface ProcessPaymentApi {


}
