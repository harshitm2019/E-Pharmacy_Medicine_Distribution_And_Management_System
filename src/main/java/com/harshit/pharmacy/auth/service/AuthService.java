package com.harshit.pharmacy.auth.service;


import com.harshit.pharmacy.auth.record.LoginRequest;
import com.harshit.pharmacy.auth.record.LoginResponse;
import com.harshit.pharmacy.auth.record.RegisterRequest;
import com.harshit.pharmacy.auth.record.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}
