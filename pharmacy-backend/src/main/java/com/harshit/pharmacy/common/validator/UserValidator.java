package com.harshit.pharmacy.common.validator;


import com.harshit.pharmacy.common.constants.FieldNames;
import com.harshit.pharmacy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    private final DuplicateValidator duplicateValidator;

    public void validateRegistration(String email, String phone) {

        duplicateValidator.validate(
                userRepository.existsByEmail(email),
                FieldNames.EMAIL
        );

        duplicateValidator.validate(
                userRepository.existsByPhone(phone),
                FieldNames.PHONE_NUMBER
        );
    }
}
