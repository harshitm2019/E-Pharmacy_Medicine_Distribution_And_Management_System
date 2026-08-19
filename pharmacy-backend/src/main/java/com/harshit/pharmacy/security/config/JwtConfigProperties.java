package com.harshit.pharmacy.security.config;


import com.harshit.pharmacy.common.constants.AppConstants;
import com.harshit.pharmacy.common.constants.SecurityConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = SecurityConstants.JWT)
public record JwtConfigProperties(

        String secretKey,
        Long expiration

) {
}
