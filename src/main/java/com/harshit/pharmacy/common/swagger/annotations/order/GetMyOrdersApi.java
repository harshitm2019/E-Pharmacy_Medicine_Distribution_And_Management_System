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
        summary = "Get My Orders",
        description = "Returns all orders placed by the currently authenticated user."
)
@ApiResponses({
        @ApiResponse(responseCode = SwaggerResponses.OK, description = SuccessMessages.ORDERS_FETCHED_SUCCESSFULLY),
        @ApiResponse(responseCode = SwaggerResponses.UNAUTHORIZED, description = SwaggerResponses.UNAUTHORIZED_DESC)
})
@SecurityRequirement(name = SwaggerConstants.BEARER_AUTH)
public @interface GetMyOrdersApi {
}
