package com.harshit.pharmacy.security.service;

import com.harshit.pharmacy.common.constants.ErrorMessages;
import com.harshit.pharmacy.security.user.CustomUserDetails;
import com.harshit.pharmacy.user.entity.User;
import com.harshit.pharmacy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

           User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(ErrorMessages.INVALID_CREDENTIALS));

           return new CustomUserDetails(user);

    }
    public UserDetails loadUserByUserId(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(
                                ErrorMessages.INVALID_CREDENTIALS));
        return new CustomUserDetails(user);
    }
}
