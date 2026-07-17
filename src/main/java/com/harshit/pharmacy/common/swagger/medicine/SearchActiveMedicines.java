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
        summary = "Search Active Medicines",
        description = "Search active medicines by medicine name."
)
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Medicines fetched successfully")
})
public @interface SearchActiveMedicines {
}
