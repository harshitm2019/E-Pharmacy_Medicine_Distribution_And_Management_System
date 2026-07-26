package com.harshit.pharmacy.security.config;

import com.harshit.pharmacy.security.jwt.JwtAuthenticationEntryPoint;
import com.harshit.pharmacy.security.jwt.JwtFilter;
import com.harshit.pharmacy.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtFilter jwtFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(customUserDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))

                .authenticationProvider(authenticationProvider())

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .authorizeHttpRequests(auth -> auth

                        // Public APIs
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/api/v1/auth/**",
                                "/api/v1/medicines/**"
                        ).permitAll()

                        // ==========================
                        // ADMIN APIs
                        // ==========================
                        .requestMatchers("/api/v1/admin/**")
                        .hasRole("ADMIN")

                        // ==========================
                        // ORDER APIs
                        // ==========================
                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/orders/checkout")
                        .hasRole("CUSTOMER")

                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/orders/**")
                        .hasRole("CUSTOMER")

                        .requestMatchers(HttpMethod.PATCH,
                                "/api/v1/orders/*")
                        .hasRole("CUSTOMER")

                        .requestMatchers(HttpMethod.PATCH,
                                "/api/v1/orders/*/cancel")
                        .hasRole("CUSTOMER")

                        // ==========================
                        // PAYMENT APIs
                        // ==========================
                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/payments/online")
                        .hasRole("CUSTOMER")

                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/payments/**")
                        .hasRole("CUSTOMER")

                        .requestMatchers(HttpMethod.PATCH,
                                "/api/v1/payments/cod/**")
                        .hasRole("DELIVERY_BOY")

                        // ==========================
                        // PRESCRIPTION APIs
                        // ==========================
                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/prescriptions/**")
                        .hasRole("CUSTOMER")

                        .requestMatchers(HttpMethod.PUT,
                                "/api/v1/prescriptions/**")
                        .hasRole("CUSTOMER")

                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/prescriptions/**")
                        .hasAnyRole("ADMIN", "CUSTOMER")

                        // ==========================
                        // USER APIs
                        // ==========================
                        .requestMatchers("/api/v1/users/**")
                        .hasAnyRole("ADMIN", "CUSTOMER", "DELIVERY_BOY")

                        // Everything else
                        .anyRequest()
                        .authenticated()
                );

        return http.build();
    }

}