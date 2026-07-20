package com.harshit.pharmacy.user.record;

import com.harshit.pharmacy.user.enums.UserRole;
import com.harshit.pharmacy.user.enums.UserStatus;

public record AdminUserResponse(


        Integer userId,
        String email,
        UserRole role,
        UserStatus status



) {
}
