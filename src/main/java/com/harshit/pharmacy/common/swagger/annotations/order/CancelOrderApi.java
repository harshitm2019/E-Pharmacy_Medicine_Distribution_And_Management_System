package com.harshit.pharmacy.common.swagger.annotations.order;


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
        summary = "Cancel Order",
        description = "Cancels an existing order. Only pending or unshipped orders can be cancelled."
)
@ApiResponses({
        @ApiResponse(responseCode = SwaggerResponses.OK, description = SuccessMessages.ORDER_CANCELLED_SUCCESSFULLY),
        @ApiResponse(responseCode = SwaggerResponses.BAD_REQUEST, description = "Order cannot be cancelled in its current state"),
        @ApiResponse(responseCode = SwaggerResponses.UNAUTHORIZED, description = SwaggerResponses.UNAUTHORIZED_DESC),
        @ApiResponse(responseCode = SwaggerResponses.FORBIDDEN, description = "You do not have permission to cancel this order"),
        @ApiResponse(responseCode = SwaggerResponses.NOT_FOUND, description = "Order not found")
})
@SecurityRequirement(name = SwaggerConstants.BEARER_AUTH)
public @interface CancelOrderApi {
}
