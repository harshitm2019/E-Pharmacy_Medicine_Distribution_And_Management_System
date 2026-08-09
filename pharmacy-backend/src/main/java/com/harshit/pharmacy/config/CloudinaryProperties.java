package com.harshit.pharmacy.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "cloudinary")
public record CloudinaryProperties(


        String cloudName,

        String apiKey,

        String apiSecret


) {
}
