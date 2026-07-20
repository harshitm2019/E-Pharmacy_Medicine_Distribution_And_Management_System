package com.harshit.pharmacy.user.controller;


import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.response.ApiResponse;
import com.harshit.pharmacy.common.swagger.annotations.user.ChangeEmailApi;
import com.harshit.pharmacy.common.swagger.annotations.user.ChangePasswordApi;
import com.harshit.pharmacy.common.swagger.annotations.user.GetMyProfileApi;
import com.harshit.pharmacy.common.swagger.annotations.user.UpdateProfileApi;
import com.harshit.pharmacy.common.swagger.constants.SwaggerConstants;
import com.harshit.pharmacy.user.record.*;
import com.harshit.pharmacy.user.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(
        name = SwaggerConstants.USER_TAG,
        description = SwaggerConstants.USER_TAG_DESCRIPTION
)
@SecurityRequirement(name = SwaggerConstants.BEARER_AUTH)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMyProfileApi
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getMyProfile() {

        UserProfileResponse response = userService.getMyProfile();

        return ResponseEntity.status(HttpStatus.OK).body(

                ApiResponse.success(SuccessMessages.USER_PROFILE_FETCHED_SUCCESSFULLY, response)

        );
    }

    @UpdateProfileApi
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateProfile(
            @Valid @RequestBody UserProfileRequest request) {

        UserProfileResponse response = userService.updateProfile(request);

        return ResponseEntity.status(HttpStatus.OK).body(

                ApiResponse.success(SuccessMessages.USER_PROFILE_UPDATED_SUCCESSFULLY, response)

        );
    }

    @ChangePasswordApi
    @PatchMapping("/me/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @Valid @RequestBody ChangePasswordRequest request) {

        userService.changePassword(request);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.PASSWORD_CHANGED)
        );
    }

    @ChangeEmailApi
    @PatchMapping("/me/change-email")
    public ResponseEntity<ApiResponse<Void>> changeEmail(
            @Valid @RequestBody ChangeEmailRequest request) {

        userService.changeEmail(request);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.EMAIL_CHANGED)
        );
    }

}
