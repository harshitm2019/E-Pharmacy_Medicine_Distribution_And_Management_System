package com.harshit.pharmacy.user.mapper;

import com.harshit.pharmacy.security.service.PasswordService;
import com.harshit.pharmacy.user.record.*;
import com.harshit.pharmacy.user.record.UserProfileResponse;
import com.harshit.pharmacy.user.entity.User;
import com.harshit.pharmacy.user.entity.UserProfile;
import com.harshit.pharmacy.user.enums.UserRole;
import com.harshit.pharmacy.user.enums.UserStatus;
import com.harshit.pharmacy.user.record.AdminUserResponse;
import com.harshit.pharmacy.user.record.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class UserMapper {

    private  final PasswordService  passwordService;

    public  User toUser(CreateUserRequest request) {

        return User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordService.encode(request.password()))
                .phone(request.phoneNumber())
                .status(UserStatus.ACTIVE)
                .role(UserRole.valueOf(request.role()))
                .build();
    }

    public  UserProfile toUserProfile(CreateUserRequest request, User user) {

        return UserProfile.builder()
                .user(user)
                .address(request.address())
                .city(request.city())
                .state(request.state())
                .pin(request.pin())
                .build();
    }

    public  AdminUserResponse toAdminUserResponse(User user) {

        return new AdminUserResponse(
                user.getUserId(),
                user.getEmail(),
                user.getRole(),
                user.getStatus()
        );
    }

    public  UserProfileResponse toUserProfileResponse(User user, UserProfile profile) {

        return new UserProfileResponse(
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                profile.getAddress(),
                profile.getCity(),
                profile.getState(),
                profile.getPin()
        );
    }

    public  void updateUserProfile(UserProfileRequest request,
                                         User user,
                                         UserProfile profile) {

        user.setUsername(request.username());
        user.setPhone(request.phoneNumber());
        profile.setAddress(request.address());
        profile.setCity(request.city());
        profile.setState(request.state());
        profile.setPin(request.pin());

    }
}