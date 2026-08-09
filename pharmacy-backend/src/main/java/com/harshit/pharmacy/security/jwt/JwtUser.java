package com.harshit.pharmacy.security.jwt;

import com.harshit.pharmacy.user.enums.UserRole;

public record JwtUser(

        Integer userId,

        String email,

        UserRole role

) {
}
