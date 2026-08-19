package com.harshit.pharmacy.user.service;

import com.harshit.pharmacy.user.dto.AdminUserResponse;
import com.harshit.pharmacy.user.dto.ChangeEmailRequest;
import com.harshit.pharmacy.user.dto.ChangePasswordRequest;
import com.harshit.pharmacy.user.dto.CreateUserRequest;
import com.harshit.pharmacy.user.dto.UserProfileRequest;
import com.harshit.pharmacy.user.dto.UserProfileResponse;
import com.harshit.pharmacy.user.dto.UserStatusRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    AdminUserResponse createUser(CreateUserRequest request);

    Page<AdminUserResponse> getUsers(String role, String email, Pageable pageable);

    AdminUserResponse updateUserStatus(Integer userId, UserStatusRequest request);

    UserProfileResponse getMyProfile();

    UserProfileResponse updateProfile(UserProfileRequest request);

    void changeEmail(ChangeEmailRequest request);

    void changePassword(ChangePasswordRequest request);
}