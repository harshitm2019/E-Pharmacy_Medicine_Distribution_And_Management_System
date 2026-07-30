package com.harshit.pharmacy;

import com.harshit.pharmacy.config.AdminProperties;
import com.harshit.pharmacy.config.CloudinaryProperties;
import com.harshit.pharmacy.config.FileProperties;
import com.harshit.pharmacy.security.config.JwtConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({JwtConfigProperties.class, AdminProperties.class,
                                CloudinaryProperties.class, FileProperties.class})
public class PharmacyApplication {

	public static void main(String[] args) {
		SpringApplication.run(PharmacyApplication.class, args);
	}

}
