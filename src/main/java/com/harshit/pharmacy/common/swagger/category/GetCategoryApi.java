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
        summary = "Get Category By Id",
        description = "Returns a medicine category by its identifier."
)
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Category fetched successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Category not found")
})
@SecurityRequirement(name = "Bearer Authentication")
public @interface GetCategoryApi {
}
