package com.harshit.pharmacy.config;

import com.harshit.pharmacy.security.config.JwtConfigProperties;
import com.harshit.pharmacy.user.entity.User;
import com.harshit.pharmacy.user.enums.UserRole;
import com.harshit.pharmacy.user.enums.UserStatus;
import com.harshit.pharmacy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminProperties adminProperties;


    @Override
    public void run(String... args) throws Exception {

        if (userRepository.existsByRole(UserRole.ADMIN)) {
            return;
        }

        User admin = User.builder()
                .username(adminProperties.username())
                .email(adminProperties.email())
                .phone(adminProperties.phone())
                .password(passwordEncoder.encode(adminProperties.password()))
                .role(UserRole.ADMIN)
                .status(UserStatus.ACTIVE)
                .build();

        userRepository.save(admin);



    }
}
