package com.harshit.pharmacy.user.dto;

import com.harshit.pharmacy.common.constants.ErrorMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ChangeEmailRequest(

        @NotBlank(message = ErrorMessages.EMAIL_IS_REQUIRED)
        @Email(message = ErrorMessages.INVALID_EMAIL)
        String newEmail,

        @NotBlank(message = ErrorMessages.PASSWORD_IS_REQUIRED)
        String password

) {
}
