package com.harshit.pharmacy.user.controller;


import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.response.ApiResponse;
import com.harshit.pharmacy.user.dto.*;
import com.harshit.pharmacy.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getMyProfile() {

        UserProfileResponse response = userService.getMyProfile();

        return ResponseEntity.status(HttpStatus.OK).body(

                ApiResponse.success(SuccessMessages.USER_PROFILE_FETCHED_SUCCESSFULLY, response)

        );
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateProfile(
            @Valid @RequestBody UserProfileRequest request) {

        UserProfileResponse response = userService.updateProfile(request);

        return ResponseEntity.status(HttpStatus.OK).body(

                ApiResponse.success(SuccessMessages.USER_PROFILE_UPDATED_SUCCESSFULLY, response)

        );
    }

    @PatchMapping("/me/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @Valid @RequestBody ChangePasswordRequest request) {

        userService.changePassword(request);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.PASSWORD_CHANGED)
        );
    }

    @PatchMapping("/me/change-email")
    public ResponseEntity<ApiResponse<Void>> changeEmail(
            @Valid @RequestBody ChangeEmailRequest request) {

        userService.changeEmail(request);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.EMAIL_CHANGED)
        );
    }

}
