package com.harshit.pharmacy.common.swagger.annotations.category;


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
        summary = "Get Category By Id",
        description = "Returns a medicine category by its identifier."
)
@ApiResponses({
        @ApiResponse(responseCode = SwaggerResponses.OK, description = "Category fetched successfully"),
        @ApiResponse(responseCode = SwaggerResponses.UNAUTHORIZED, description = SwaggerResponses.UNAUTHORIZED_DESC),
        @ApiResponse(responseCode = SwaggerResponses.NOT_FOUND, description = "Category not found")
})
@SecurityRequirement(name = SwaggerConstants.BEARER_AUTH)
public @interface GetCategoryApi {
}