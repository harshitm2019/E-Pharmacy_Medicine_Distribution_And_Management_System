package com.harshit.pharmacy.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harshit.pharmacy.common.constants.AppConstants;
import com.harshit.pharmacy.common.constants.ErrorMessages;
import com.harshit.pharmacy.common.constants.SecurityConstants;
import com.harshit.pharmacy.common.response.ApiResponse;
import com.harshit.pharmacy.security.service.CustomUserDetailsService;
import com.harshit.pharmacy.security.user.CustomUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);

        if (authorizationHeader == null
                || !authorizationHeader.startsWith(SecurityConstants.BEARER_PREFIX)) {

            filterChain.doFilter(request, response);
            return;
        }
        try {

            String token = authorizationHeader.substring(
                    SecurityConstants.BEARER_PREFIX.length());

            Integer userId = jwtService.extractUserId(token);

            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUserId(userId);

                if (!userDetails.isEnabled()) {
                    throw new DisabledException(
                            ErrorMessages.ACCOUNT_DISABLED
                    );
                }

                if (jwtService.isTokenValid(token, userDetails)) {

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null,
                                    userDetails.getAuthorities()
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        }
        catch (ExpiredJwtException ex) {

            handleJwtException(response,ErrorMessages.JWT_TOKEN_EXPIRED);
            return;

        }
        catch (MalformedJwtException ex) {

            handleJwtException(response,ErrorMessages.INVALID_JWT_TOKEN);
            return;

        }
        catch (SignatureException ex) {

            handleJwtException(response,ErrorMessages.INVALID_JWT_SIGNATURE);
            return;

        }
        catch (UnsupportedJwtException ex) {

            handleJwtException(response,ErrorMessages.UNSUPPORTED_JWT_TOKEN);
            return;

        }
        catch (IllegalArgumentException ex) {

            handleJwtException(response,ErrorMessages.JWT_TOKEN_EMPTY);
            return;

        }
        catch (JwtException ex) {

            handleJwtException(response,ErrorMessages.INVALID_JWT_TOKEN);
            return;

        }
        filterChain.doFilter(request, response);
    }
    private void handleJwtException(HttpServletResponse response,String message) throws IOException {
        SecurityContextHolder.clearContext();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(
                response.getOutputStream(),
                ApiResponse.failure(message, null)
        );
    }
}
