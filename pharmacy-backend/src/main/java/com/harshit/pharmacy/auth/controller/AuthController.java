package com.harshit.pharmacy.auth.controller;

import com.harshit.pharmacy.auth.dto.LoginRequest;
import com.harshit.pharmacy.auth.dto.LoginResponse;
import com.harshit.pharmacy.auth.dto.RegisterRequest;
import com.harshit.pharmacy.auth.dto.RegisterResponse;
import com.harshit.pharmacy.auth.service.AuthService;
import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {


       private final AuthService authService;

       @PostMapping("/register")
       public ResponseEntity<ApiResponse<RegisterResponse>>
                  register(@Valid @RequestBody RegisterRequest registerRequest){


             RegisterResponse registerResponse = authService.register(registerRequest);

             return ResponseEntity.status(HttpStatus.CREATED).body(

                     ApiResponse.success(SuccessMessages.REGISTRATION_SUCCESSFUL, registerResponse)
             );

       }

      @PostMapping("/login")
      public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response =
                authService.login(request);

        return ResponseEntity.status(HttpStatus.OK).body(

                ApiResponse.success(SuccessMessages.LOGIN_SUCCESSFUL, response)

        );
    }

}
