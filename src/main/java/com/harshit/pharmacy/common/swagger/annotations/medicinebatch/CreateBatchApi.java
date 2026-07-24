package com.harshit.pharmacy.common.swagger.annotations.medicinebatch;

import com.harshit.pharmacy.common.constants.ErrorMessages;
import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.swagger.constants.SwaggerConstants;
import com.harshit.pharmacy.common.swagger.constants.SwaggerResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Create Batch", description = "Creates a new batch for an existing medicine.")
@ApiResponses({
        @ApiResponse(responseCode = SwaggerResponses.CREATED, description = SuccessMessages.BATCH_CREATED),
        @ApiResponse(responseCode = SwaggerResponses.BAD_REQUEST, description = SwaggerResponses.VALIDATION_FAILED),
        @ApiResponse(responseCode = SwaggerResponses.UNAUTHORIZED, description = SwaggerResponses.UNAUTHORIZED_DESC),
        @ApiResponse(responseCode = SwaggerResponses.FORBIDDEN, description = SwaggerResponses.ACCESS_DENIED),
        @ApiResponse(responseCode = SwaggerResponses.NOT_FOUND, description = ErrorMessages.MEDICINE_DOES_NOT_EXIST),
        @ApiResponse(responseCode = SwaggerResponses.CONFLICT, description = ErrorMessages.BATCH_NUMBER_ALREADY_EXISTS)
})
@SecurityRequirement(name = SwaggerConstants.BEARER_AUTH)
public @interface CreateBatchApi {
}