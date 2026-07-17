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
        summary = "Get Medicine By ID",
        description = "Returns an active medicine using its ID."
)
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Medicine fetched successfully"),
        @ApiResponse(responseCode = "404", description = "Medicine not found")
})
public @interface GetActiveMedicineById {
}
