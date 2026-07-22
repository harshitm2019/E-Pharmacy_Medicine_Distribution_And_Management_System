package com.harshit.pharmacy.auth.dto;

import com.harshit.pharmacy.common.constants.AppConstants;
import com.harshit.pharmacy.common.constants.ErrorMessages;
import jakarta.validation.constraints.*;

public record RegisterRequest(

        @NotBlank(message = ErrorMessages.USERNAME_IS_REQUIRED)
        @Size(min = 3, max = 100)
        String username,

        @Email(message = ErrorMessages.INVALID_EMAIL)
        @NotBlank(message = ErrorMessages.EMAIL_IS_REQUIRED)
        String email,

        @NotBlank(message = ErrorMessages.PHONE_NUMBER_IS_REQUIRED)
        @Pattern(regexp = AppConstants.PHONE_NUMBER_PATTERN, message = ErrorMessages.INVALID_PHONE_NUMBER)
        String phone,

        @NotBlank(message = ErrorMessages.PASSWORD_IS_REQUIRED)
        @Size(min = 8, max = 20)
        String password,

        @NotBlank(message = ErrorMessages.ADDRESS_IS_REQUIRED)
        String address,

        @NotBlank(message = ErrorMessages.CITY_IS_REQUIRED)
        String city,

        @NotBlank(message = ErrorMessages.STATE_IS_REQUIRED)
        String state,

        @NotBlank(message = ErrorMessages.PIN_IS_REQUIRED)
        @Pattern(regexp = AppConstants.PIN_PATTERN, message = ErrorMessages.INVALID_PIN)
        String pin

) {
}