package com.harshit.pharmacy.user.record;

import com.harshit.pharmacy.common.constants.ErrorMessages;
import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(


        @NotBlank(message = ErrorMessages.OLD_PASSWORD_IS_REQUIRED)
        String oldPassword,

        @NotBlank(message = ErrorMessages.NEW_PASSWORD_IS_REQUIRED)
        String newPassword,

        @NotBlank(message = ErrorMessages.CONFIRM_PASSWORD_IS_REQUIRED)
        String confirmPassword


) {
}
