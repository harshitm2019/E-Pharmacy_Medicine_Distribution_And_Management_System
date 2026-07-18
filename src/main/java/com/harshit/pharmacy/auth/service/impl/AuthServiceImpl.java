package com.harshit.pharmacy.auth.service.impl;

import com.harshit.pharmacy.auth.record.LoginRequest;
import com.harshit.pharmacy.auth.record.LoginResponse;
import com.harshit.pharmacy.auth.record.RegisterRequest;
import com.harshit.pharmacy.auth.record.RegisterResponse;
import com.harshit.pharmacy.auth.mapper.AuthMapper;
import com.harshit.pharmacy.auth.service.AuthService;
import com.harshit.pharmacy.common.validator.UserValidator;
import com.harshit.pharmacy.security.jwt.JwtService;
import com.harshit.pharmacy.security.jwt.JwtUser;
import com.harshit.pharmacy.security.service.PasswordService;
import com.harshit.pharmacy.security.user.CustomUserDetails;
import com.harshit.pharmacy.user.entity.User;
import com.harshit.pharmacy.user.entity.UserProfile;
import com.harshit.pharmacy.user.enums.UserRole;
import com.harshit.pharmacy.user.enums.UserStatus;
import com.harshit.pharmacy.user.repository.UserProfileRepository;
import com.harshit.pharmacy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final AuthMapper authMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Override
    public RegisterResponse register(RegisterRequest request) {

         userValidator.validateRegistration(request.email(), request.phone());

         User user = authMapper.toUser(request);

         userRepository.save(user);

         UserProfile userProfile = authMapper.toUserProfile(request,user, LocalDateTime.now());

         userProfileRepository.save(userProfile);

         return authMapper.toRegisterResponse(user);


    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.email(),
                                request.password()
                        )
                );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        JwtUser jwtUser = new JwtUser(
                user.getUserId(),
                user.getEmail(),
                user.getRole()
        );

        String accessToken = jwtService.generateToken(jwtUser);

        return authMapper.toLoginResponse(user, accessToken);
    }
}
