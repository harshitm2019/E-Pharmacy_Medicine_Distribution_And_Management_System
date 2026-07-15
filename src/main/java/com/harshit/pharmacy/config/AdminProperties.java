package com.harshit.pharmacy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.admin")
public record AdminProperties (

     String username,
     String email,
     String phone,
     String password


)
{}
