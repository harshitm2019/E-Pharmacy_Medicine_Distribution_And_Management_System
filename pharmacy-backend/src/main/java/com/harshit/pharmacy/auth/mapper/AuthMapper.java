package com.harshit.pharmacy.auth.mapper;

import com.harshit.pharmacy.auth.dto.LoginResponse;
import com.harshit.pharmacy.auth.dto.RegisterRequest;
import com.harshit.pharmacy.auth.dto.RegisterResponse;
import com.harshit.pharmacy.security.service.PasswordService;
import com.harshit.pharmacy.user.entity.User;
import com.harshit.pharmacy.user.entity.UserProfile;
import com.harshit.pharmacy.user.enums.UserRole;
import com.harshit.pharmacy.user.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AuthMapper {

    private final PasswordService  passwordService;

    public User toUser(RegisterRequest request) {

        return User.builder()
                .username(request.username())
                .email(request.email())
                .phone(request.phone())
                .password(passwordService.encode(request.password()))
                .status(UserStatus.ACTIVE)
                .role(UserRole.CUSTOMER)
                .build();

    }

    public UserProfile toUserProfile(RegisterRequest request, User user,LocalDateTime now) {


        return UserProfile.builder()
                .user(user)
                .address(request.address())
                .city(request.city())
                .state(request.state())
                .pin(request.pin())
                .build();

    }

    public RegisterResponse toRegisterResponse(User user) {

        return new RegisterResponse(

                user.getUserId(),
                user.getUsername()

        );
    }

    public LoginResponse toLoginResponse(User user,String accessToken) {

        return new LoginResponse(
                user.getUserId(),
                user.getUsername(),
                user.getRole(),
                accessToken
        );

    }

}
