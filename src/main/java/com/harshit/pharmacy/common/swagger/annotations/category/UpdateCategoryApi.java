package com.harshit.pharmacy.common.swagger.annotations.category;


import com.harshit.pharmacy.common.constants.ErrorMessages;
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
        summary = "Update Category",
        description = "Updates an existing medicine category. Accessible only by ADMIN."
)
@ApiResponses({
        @ApiResponse(responseCode = SwaggerResponses.OK, description = SuccessMessages.CATEGORY_UPDATED),
        @ApiResponse(responseCode = SwaggerResponses.BAD_REQUEST, description = SwaggerResponses.VALIDATION_FAILED),
        @ApiResponse(responseCode = SwaggerResponses.UNAUTHORIZED, description = SwaggerResponses.UNAUTHORIZED_DESC),
        @ApiResponse(responseCode = SwaggerResponses.FORBIDDEN, description = SwaggerResponses.FORBIDDEN_DESC),
        @ApiResponse(responseCode = SwaggerResponses.NOT_FOUND, description = ErrorMessages.CATEGORY_DOES_NOT_EXIST),
        @ApiResponse(responseCode = SwaggerResponses.CONFLICT, description = ErrorMessages.CATEGORY_ALREADY_EXISTS)
})
@SecurityRequirement(name = SwaggerConstants.BEARER_AUTH)
public @interface UpdateCategoryApi {
}