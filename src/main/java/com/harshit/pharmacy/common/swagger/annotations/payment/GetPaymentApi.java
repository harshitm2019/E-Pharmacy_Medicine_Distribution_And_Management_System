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
        summary = "Get Payment By ID",
        description = "Returns a specific payment by its ID. Accessible by the owner or an ADMIN."
)
@ApiResponses({
        @ApiResponse(responseCode = SwaggerResponses.OK, description = SuccessMessages.PAYMENT_FETCHED_SUCCESSFULLY),
        @ApiResponse(responseCode = SwaggerResponses.UNAUTHORIZED, description = SwaggerResponses.UNAUTHORIZED_DESC),
        @ApiResponse(responseCode = SwaggerResponses.FORBIDDEN, description = "You do not have permission to view this payment"),
        @ApiResponse(responseCode = SwaggerResponses.NOT_FOUND, description = "Payment not found")
})
@SecurityRequirement(name = SwaggerConstants.BEARER_AUTH)
public @interface GetPaymentApi {
}
