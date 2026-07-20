package com.harshit.pharmacy.user.controller;

import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.response.ApiResponse;
import com.harshit.pharmacy.common.swagger.annotations.user.CreateUserApi;
import com.harshit.pharmacy.common.swagger.annotations.user.GetAllUsersApi;
import com.harshit.pharmacy.common.swagger.annotations.user.SearchUsersApi;
import com.harshit.pharmacy.common.swagger.annotations.user.UpdateUserStatusApi;
import com.harshit.pharmacy.common.swagger.constants.SwaggerConstants;
import com.harshit.pharmacy.user.record.AdminUserResponse;
import com.harshit.pharmacy.user.record.CreateUserRequest;
import com.harshit.pharmacy.user.record.UserStatusRequest;
import com.harshit.pharmacy.user.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(
        name = SwaggerConstants.ADMIN_USER_TAG,
        description = SwaggerConstants.ADMIN_USER_TAG_DESCRIPTION
)
@SecurityRequirement(name = SwaggerConstants.BEARER_AUTH)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/users")
public class AdminUserController {

    private final UserService userService;

    @CreateUserApi
    @PostMapping
    public ResponseEntity<ApiResponse<AdminUserResponse>> createUser(@Valid @RequestBody CreateUserRequest request) {

       AdminUserResponse response = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(SuccessMessages.USER_CREATED, response)
        );

    }

    @GetAllUsersApi
    @GetMapping
    public ResponseEntity<ApiResponse<Page<AdminUserResponse>>> getAllUsers(
            @ParameterObject
            @PageableDefault(sort = "userId")
            Pageable pageable) {

        Page<AdminUserResponse> response = userService.getAllUsers(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(

                ApiResponse.success(SuccessMessages.USER_FETCHED_SUCCESSFULLY,response)

        );

    }

    @SearchUsersApi
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<AdminUserResponse>>> searchUsers(
            @RequestParam String email,
            @ParameterObject
            @PageableDefault(sort = "userId")
            Pageable pageable) {

        Page<AdminUserResponse> users = userService.searchUsers(email, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(

                ApiResponse.success(SuccessMessages.USER_FETCHED_SUCCESSFULLY, users)

        );

    }

    @UpdateUserStatusApi
    @PatchMapping("/{userId}/status")
    public ResponseEntity<ApiResponse<AdminUserResponse>> updateUserStatus(
            @PathVariable Integer userId,
            @Valid @RequestBody UserStatusRequest request) {

        AdminUserResponse userResponse = userService.updateUserStatus(userId, request);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.USER_STATUS_UPDATED, userResponse)
        );
    }





}
