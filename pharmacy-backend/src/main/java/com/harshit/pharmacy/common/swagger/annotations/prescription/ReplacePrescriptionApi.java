package com.harshit.pharmacy.common.swagger.annotations.prescription;

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
        summary = "Replace Prescription",
        description = "Replaces the file of an existing prescription. Only pending or rejected prescriptions can be replaced."
)
@ApiResponses({
        @ApiResponse(responseCode = SwaggerResponses.OK, description = SuccessMessages.PRESCRIPTION_REPLACED),
        @ApiResponse(responseCode = SwaggerResponses.BAD_REQUEST, description = ErrorMessages.CANNOT_MODIFY_APPROVED_PRESCRIPTION),
        @ApiResponse(responseCode = SwaggerResponses.UNAUTHORIZED, description = SwaggerResponses.UNAUTHORIZED_DESC),
        @ApiResponse(responseCode = SwaggerResponses.FORBIDDEN, description = SwaggerResponses.ACCESS_DENIED),
        @ApiResponse(responseCode = SwaggerResponses.NOT_FOUND, description = ErrorMessages.PRESCRIPTION_NOT_FOUND)
})
@SecurityRequirement(name = SwaggerConstants.BEARER_AUTH)
public @interface ReplacePrescriptionApi {
}