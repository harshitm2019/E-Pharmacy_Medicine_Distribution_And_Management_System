package com.harshit.pharmacy.common.swagger.annotations.medicine;


import com.harshit.pharmacy.common.constants.ErrorMessages;
import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.swagger.constants.SwaggerResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Create Medicine",
        description = "Creates a new medicine in the system."
)
@ApiResponses({
        @ApiResponse(responseCode = SwaggerResponses.CREATED, description = SuccessMessages.MEDICINE_CREATED),
        @ApiResponse(responseCode = SwaggerResponses.BAD_REQUEST, description = SwaggerResponses.VALIDATION_FAILED),
        @ApiResponse(responseCode = SwaggerResponses.UNAUTHORIZED, description = SwaggerResponses.UNAUTHORIZED_DESC),
        @ApiResponse(responseCode = SwaggerResponses.FORBIDDEN, description = SwaggerResponses.ACCESS_DENIED),
        @ApiResponse(responseCode = SwaggerResponses.NOT_FOUND, description = ErrorMessages.CATEGORY_DOES_NOT_EXIST),
        @ApiResponse(responseCode = SwaggerResponses.CONFLICT, description = ErrorMessages.BATCH_NUMBER_ALREADY_EXISTS)
})
public @interface CreateMedicine {
}