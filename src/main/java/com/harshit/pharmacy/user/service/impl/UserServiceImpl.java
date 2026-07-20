package com.harshit.pharmacy.user.service.impl;

import com.harshit.pharmacy.common.constants.ErrorMessages;
import com.harshit.pharmacy.common.constants.FieldNames;
import com.harshit.pharmacy.common.validator.DuplicateValidator;
import com.harshit.pharmacy.common.validator.UserValidator;
import com.harshit.pharmacy.exception.BadRequestException;
import com.harshit.pharmacy.exception.InvalidRequestException;
import com.harshit.pharmacy.exception.ResourceNotFoundException;
import com.harshit.pharmacy.security.service.PasswordService;
import com.harshit.pharmacy.security.utils.SecurityUtils;
import com.harshit.pharmacy.user.entity.User;
import com.harshit.pharmacy.user.entity.UserProfile;
import com.harshit.pharmacy.user.enums.UserStatus;
import com.harshit.pharmacy.user.mapper.UserMapper;
import com.harshit.pharmacy.user.record.*;
import com.harshit.pharmacy.user.repository.UserProfileRepository;
import com.harshit.pharmacy.user.repository.UserRepository;
import com.harshit.pharmacy.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserValidator userValidator;
    private final UserMapper userMapper;
    private final SecurityUtils securityUtils;
    private final PasswordService  passwordService;
    private final DuplicateValidator duplicateValidator;


    @Override
    public AdminUserResponse createUser(CreateUserRequest request) {

        userValidator.validateRegistration(request.email(),request.phoneNumber());

        User user = userMapper.toUser(request);
        userRepository.save(user);

        UserProfile profile = userMapper.toUserProfile(request, user);
        userProfileRepository.save(profile);

        return userMapper.toAdminUserResponse(user);

    }

    @Override
    public Page<AdminUserResponse> getAllUsers(Pageable pageable) {

        return userRepository.findAll(pageable).map(userMapper::toAdminUserResponse);

    }

    @Override
    public Page<AdminUserResponse> searchUsers(String email, Pageable pageable) {

        return userRepository.findByEmailContainingIgnoreCase(email, pageable).map(userMapper::toAdminUserResponse);

    }

    @Override
    public AdminUserResponse updateUserStatus(Integer userId, UserStatusRequest request) {

        User user = userRepository.findById(userId)
                                  .orElseThrow(() ->
                                  new ResourceNotFoundException(ErrorMessages.USER_DOES_NOT_EXIST));

        user.setStatus(UserStatus.valueOf(request.status()));

        return userMapper.toAdminUserResponse(userRepository.save(user));

    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getMyProfile() {

        User user = securityUtils.getCurrentUser();

        UserProfile profile = getUserProfile(user);

        return userMapper.toUserProfileResponse(user,profile);

    }

    @Override
    public UserProfileResponse updateProfile(UserProfileRequest request) {

        User user = securityUtils.getCurrentUser();

        UserProfile profile = getUserProfile(user);


        userMapper.updateUserProfile(request, user, profile);

        userRepository.save(user);
        userProfileRepository.save(profile);

        return userMapper.toUserProfileResponse(user, profile);
    }

    @Override
    public void changeEmail(ChangeEmailRequest request) {

        User user = securityUtils.getCurrentUser();

        if (!passwordService.matches(request.password(), user.getPassword())) {
            throw new InvalidRequestException(ErrorMessages.INVALID_PASSWORD);
        }

        duplicateValidator.validate(

               userRepository.existsByEmailAndUserIdNot(request.newEmail(),user.getUserId()),
                FieldNames.EMAIL

        );

        user.setEmail(request.newEmail());

        userRepository.save(user);

    }

    @Override
    public void changePassword(ChangePasswordRequest request) {

        User user = securityUtils.getCurrentUser();

        if (!passwordService.matches(request.oldPassword(), user.getPassword())) {
            throw new BadRequestException(ErrorMessages.OLD_PASSWORD_INCORRECT);
        }

        if (!request.newPassword().equals(request.confirmPassword())) {
            throw new InvalidRequestException(ErrorMessages.PASSWORDS_DO_NOT_MATCH);
        }

        if (passwordService.matches(request.newPassword(), user.getPassword())) {
            throw new InvalidRequestException(ErrorMessages.NEW_PASSWORD_SAME_AS_OLD);
        }

        user.setPassword(passwordService.encode(request.newPassword()));
        userRepository.save(user);

    }

    private UserProfile getUserProfile(User user) {

        return userProfileRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorMessages.USER_DOES_NOT_EXIST));
    }


}
