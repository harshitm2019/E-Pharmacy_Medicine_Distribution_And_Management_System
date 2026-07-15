package com.harshit.pharmacy.auth.dto;

import com.harshit.pharmacy.common.constants.AppConstants;
import com.harshit.pharmacy.common.constants.ErrorMessages;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = ErrorMessages.EMAIL_IS_REQUIRED)
        String email,

        @NotBlank(message = ErrorMessages.PASSWORD_IS_REQUIRED)
        String password

) {


}
