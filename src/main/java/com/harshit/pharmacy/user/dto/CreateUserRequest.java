package com.harshit.pharmacy.user.dto;

import com.harshit.pharmacy.common.constants.AppConstants;
import com.harshit.pharmacy.common.constants.ErrorMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(

        @NotBlank(message = ErrorMessages.USERNAME_IS_REQUIRED)
        @Size(min = 3,max = 100)
        String username,

        @NotBlank(message = ErrorMessages.EMAIL_IS_REQUIRED)
        @Email(message = ErrorMessages.INVALID_EMAIL)
        String email,

        @NotBlank(message = ErrorMessages.PASSWORD_IS_REQUIRED)
        @Size(min = 8, max = 20)
        String password,

        @NotBlank(message = ErrorMessages.PHONE_NUMBER_IS_REQUIRED)
        @Pattern(
                regexp = AppConstants.PHONE_NUMBER_PATTERN,
                message = ErrorMessages.INVALID_PHONE_NUMBER
        )
        String phoneNumber,

        @NotBlank(message = ErrorMessages.ADDRESS_IS_REQUIRED)
        String address,

        @NotBlank(message = ErrorMessages.CITY_IS_REQUIRED)
        String city,

        @NotBlank(message = ErrorMessages.STATE_IS_REQUIRED)
        String state,

        @NotBlank(message = ErrorMessages.PIN_IS_REQUIRED)
        @Pattern(regexp = AppConstants.PIN_PATTERN, message = ErrorMessages.INVALID_PIN)
        String pin,

        @NotBlank(message = ErrorMessages.ROLE_IS_REQUIRED)
        @Pattern(regexp = "^(ADMIN|DELIVERY_BOY)$", message = "Role must be ADMIN or DELIVERY_BOY.")
        String role


) {
}
