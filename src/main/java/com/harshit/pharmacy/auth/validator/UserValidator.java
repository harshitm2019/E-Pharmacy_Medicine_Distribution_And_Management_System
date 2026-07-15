package com.harshit.pharmacy.auth.validator;


import com.harshit.pharmacy.auth.dto.RegisterRequest;
import com.harshit.pharmacy.common.constants.AppConstants;
import com.harshit.pharmacy.common.constants.ErrorMessages;
import com.harshit.pharmacy.common.constants.FieldNames;
import com.harshit.pharmacy.common.validator.DuplicateValidator;
import com.harshit.pharmacy.exception.DuplicateResourceException;
import com.harshit.pharmacy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    private final DuplicateValidator duplicateValidator;

    public void validateRegistration(RegisterRequest request) {


        duplicateValidator.validate(
                userRepository.existsByEmail(request.email()),
                FieldNames.EMAIL
        );

        duplicateValidator.validate(
                userRepository.existsByPhone(request.phone()),
                FieldNames.PHONE_NUMBER
        );
    }
}
