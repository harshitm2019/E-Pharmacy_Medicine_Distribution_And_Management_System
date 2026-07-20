package com.harshit.pharmacy.user.service;

import com.harshit.pharmacy.user.record.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    AdminUserResponse createUser(CreateUserRequest request);

    Page<AdminUserResponse> getAllUsers(Pageable pageable);

    Page<AdminUserResponse> searchUsers(String email, Pageable pageable);

    AdminUserResponse updateUserStatus(Integer userId, UserStatusRequest request);

    UserProfileResponse getMyProfile();

    UserProfileResponse updateProfile(UserProfileRequest request);

    void changeEmail(ChangeEmailRequest request);

    void changePassword(ChangePasswordRequest request);
}
