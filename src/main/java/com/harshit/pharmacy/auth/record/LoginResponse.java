package com.harshit.pharmacy.auth.record;

import com.harshit.pharmacy.user.enums.UserRole;

public record LoginResponse(

        Integer userId,
        String username,
        UserRole role,
        String accessToken
) {
}
