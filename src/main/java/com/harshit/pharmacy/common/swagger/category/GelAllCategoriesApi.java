package com.harshit.pharmacy.common.swagger.category;


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
        summary = "Get All Categories",
        description = "Returns all available medicine categories."
)
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categories fetched successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
})
@SecurityRequirement(name = "Bearer Authentication")
public @interface GelAllCategoriesApi {
}
