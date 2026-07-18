package com.harshit.pharmacy.user.record;

import com.harshit.pharmacy.common.constants.AppConstants;
import com.harshit.pharmacy.common.constants.ErrorMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserProfileRequest(


        @NotBlank(message = ErrorMessages.USERNAME_IS_REQUIRED)
        @Size(min = 3,max = 100)
        String username,

        @NotBlank(message = ErrorMessages.PHONE_NUMBER_IS_REQUIRED)
        @Pattern(regexp = AppConstants.PHONE_NUMBER_PATTERN, message = ErrorMessages.INVALID_PHONE_NUMBER)
        String phoneNumber,

        @NotBlank(message = ErrorMessages.ADDRESS_IS_REQUIRED)
        String address,

        @NotBlank(message = ErrorMessages.CITY_IS_REQUIRED)
        String city,

        @NotBlank(message = ErrorMessages.STATE_IS_REQUIRED)
        String state,

        @NotBlank(message = ErrorMessages.PIN_IS_REQUIRED)
        @Pattern(regexp = AppConstants.PIN_PATTERN,message =  ErrorMessages.INVALID_PIN)
        String pin


) {
}
