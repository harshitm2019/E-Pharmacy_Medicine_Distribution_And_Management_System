package com.harshit.pharmacy.user.controller;

import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.response.ApiResponse;
import com.harshit.pharmacy.user.dto.AdminUserResponse;
import com.harshit.pharmacy.user.dto.CreateUserRequest;
import com.harshit.pharmacy.user.dto.UserStatusRequest;
import com.harshit.pharmacy.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/users")
public class AdminUserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<AdminUserResponse>> createUser(
            @Valid @RequestBody CreateUserRequest request) {

        AdminUserResponse response = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(SuccessMessages.USER_CREATED, response)
        );
    }
    @GetMapping
    public ResponseEntity<ApiResponse<Page<AdminUserResponse>>> getUsers(
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String email,
            @ParameterObject @PageableDefault(sort = "userId") Pageable pageable) {

        Page<AdminUserResponse> response = userService.getUsers(role, email, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.USER_FETCHED_SUCCESSFULLY, response)
        );
    }

    @PatchMapping("/{userId}/status")
    public ResponseEntity<ApiResponse<AdminUserResponse>> updateUserStatus(
            @PathVariable Integer userId,
            @Valid @RequestBody UserStatusRequest request) {

        AdminUserResponse response = userService.updateUserStatus(userId, request);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.USER_STATUS_UPDATED, response)
        );
    }
}