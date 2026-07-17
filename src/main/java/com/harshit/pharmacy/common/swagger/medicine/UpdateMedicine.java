package com.harshit.pharmacy.common.swagger.medicine;

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
        summary = "Update Medicine",
        description = "Updates an existing medicine using its ID."
)
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Medicine updated successfully"),
        @ApiResponse(responseCode = "400", description = "Validation failed"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "Medicine or Category not found"),
        @ApiResponse(responseCode = "409", description = "Batch number already exists")
})
public @interface UpdateMedicine {

}
