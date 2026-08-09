package com.harshit.pharmacy.auth.dto;

import com.harshit.pharmacy.user.enums.UserRole;

public record LoginResponse(

        Integer userId,
        String username,
        UserRole role,
        String accessToken
) {
}
