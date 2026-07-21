package com.harshit.pharmacy.security.utils;

import com.harshit.pharmacy.common.constants.ErrorMessages;
import com.harshit.pharmacy.exception.UnauthorizedException;
import com.harshit.pharmacy.security.user.CustomUserDetails;
import com.harshit.pharmacy.user.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException(ErrorMessages.AUTHENTICATION_REQUIRED);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return userDetails.getUser();
    }


}
