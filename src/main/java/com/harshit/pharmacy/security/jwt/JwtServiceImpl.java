package com.harshit.pharmacy.security.jwt;

import com.harshit.pharmacy.common.constants.FieldNames;
import com.harshit.pharmacy.security.config.JwtConfigProperties;
import com.harshit.pharmacy.security.user.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtConfigProperties jwtProperties;

    @Override
    public String generateToken(JwtUser jwtUser) {


        Map<String, Object> claims = new HashMap<>();

        claims.put(
                FieldNames.ROLE,
                jwtUser.role().name()
        );

        Date now = new Date();

        return Jwts.builder()
                .claims(claims)
                .subject(String.valueOf(jwtUser.userId()))
                .issuedAt(now)
                .expiration(new Date(
                        now.getTime() + jwtProperties.expiration()))
                .signWith(getSigningKey())
                .compact();

    }

    @Override
    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);

    }

    private <T> T extractClaim(String token,
                               Function<Claims, T> claimResolver) {

        Claims claims = extractAllClaims(token);

        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {

        Integer tokenUserId = extractUserId(token);

        CustomUserDetails customUserDetails =
                (CustomUserDetails) userDetails;

        return tokenUserId.equals(customUserDetails.getUser().getUserId())
                && !isTokenExpired(token);

    }

    @Override
    public Integer extractUserId(String token) {

        return Integer.valueOf(extractUsername(token));

    }

    @Override
    public String extractRole(String token) {

        return extractClaim(token, claims ->
                claims.get(FieldNames.ROLE, String.class));

    }

    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }


    private SecretKey getSigningKey() {

        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.secretKey());

        return Keys.hmacShaKeyFor(keyBytes);

    }



}
