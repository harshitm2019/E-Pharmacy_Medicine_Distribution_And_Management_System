package com.harshit.pharmacy.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generateToken(JwtUser jwtUser);

    String extractUsername(String token);

    Integer extractUserId(String token);

    String extractRole(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

}
