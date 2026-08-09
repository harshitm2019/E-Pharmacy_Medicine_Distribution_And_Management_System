package com.harshit.pharmacy.auth.service;


import com.harshit.pharmacy.auth.dto.LoginRequest;
import com.harshit.pharmacy.auth.dto.LoginResponse;
import com.harshit.pharmacy.auth.dto.RegisterRequest;
import com.harshit.pharmacy.auth.dto.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}
