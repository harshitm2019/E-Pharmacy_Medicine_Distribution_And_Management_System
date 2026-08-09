package com.harshit.pharmacy.security.service.impl;

import com.harshit.pharmacy.security.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {


     private  final PasswordEncoder passwordEncoder;


    @Override
    public String encode(String password) {

         return passwordEncoder.encode(password);

    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {

         return passwordEncoder.matches(rawPassword, encodedPassword);

    }
}
