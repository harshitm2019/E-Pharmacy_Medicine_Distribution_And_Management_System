package com.harshit.pharmacy.common.swagger.annotations.prescription;


import com.harshit.pharmacy.common.swagger.constants.SwaggerConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Upload Prescription",
        description = "User can upload a valid prescription"
)
@SecurityRequirement(name = SwaggerConstants.BEARER_AUTH)
public @interface UploadPrescriptionApi {



}
