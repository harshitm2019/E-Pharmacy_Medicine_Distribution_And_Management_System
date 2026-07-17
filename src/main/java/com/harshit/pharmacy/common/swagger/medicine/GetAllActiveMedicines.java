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
        summary = "Get All Active Medicines",
        description = "Returns a paginated list of all active medicines."
)
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Medicines fetched successfully")
})
public @interface GetAllActiveMedicines {
}
