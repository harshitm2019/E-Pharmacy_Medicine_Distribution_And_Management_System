package com.harshit.pharmacy.auth.mapper;

import com.harshit.pharmacy.auth.record.LoginResponse;
import com.harshit.pharmacy.auth.record.RegisterRequest;
import com.harshit.pharmacy.auth.record.RegisterResponse;
import com.harshit.pharmacy.user.entity.User;
import com.harshit.pharmacy.user.entity.UserProfile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuthMapper {

    private AuthMapper() {
    }

    public User toUser(RegisterRequest request, String encodedPassword) {

        return User.builder()
                .username(request.username())
                .email(request.email())
                .phone(request.phone())
                .password(encodedPassword)
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
