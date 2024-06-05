package com.shop.demo.service.impl;

import com.shop.demo.config.Constants;
import com.shop.demo.domain.entities.User;
import com.shop.demo.exceptions.InvalidPasswordException;
import com.shop.demo.exceptions.UserNotFoundException;
import com.shop.demo.repository.UserRepository;
import com.shop.demo.config.securityConfig.SecurityUtils;
import com.shop.demo.domain.dto.model.UserInfoDTO;
import com.shop.demo.domain.dto.request.PasswordChangeRequest;
import com.shop.demo.service.AuthService;
import com.shop.demo.service.UserService;
import com.shop.demo.utils.CustomMessage;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final CustomMessage messageResource;


    public UserServiceImpl(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        AuthService authService,
        CustomMessage messageResource
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
        this.messageResource = messageResource;
    }

    @Transactional
    @Override
    public void changePasswordForCurrentUser(PasswordChangeRequest passwordChangeRequest) {
        User currentUser = SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByUsernameIgnoreCase)
                .orElseThrow(() -> new UserNotFoundException(messageResource.get(Constants.ErrorCode.E0002, Constants.Label.CURRENT_USER)));
        String currentPassword = passwordChangeRequest.getCurrentPassword();
        String currentEncryptedPassword = currentUser.getPassword();
        String newPassword = passwordChangeRequest.getNewPassword();
        if (!passwordEncoder.matches(currentPassword, currentEncryptedPassword)) {
            throw new InvalidPasswordException(
                    "currentPassword",
                    messageResource.get(Constants.ErrorCode.E0008, Constants.Label.CURRENT_PASSWORD));
        }
        if (currentPassword.equals(newPassword)) {
            throw new InvalidPasswordException(
                    "newPassword",
                    messageResource.get(Constants.ErrorCode.E0009, Constants.Label.NEW_PASSWORD, Constants.Label.CURRENT_PASSWORD_LOWER));
        }
        changePassword(currentUser, newPassword);
    }

    @Transactional
    @Override
    public void changePassword(User user, String newPassword) {
        String encryptedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encryptedPassword);
        userRepository.save(user);
        authService.clearUserCaches(user.getUsername());
    }

    @Override
    public Optional<UserInfoDTO> getCurrentUserInfo() {
        User user = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsernameIgnoreCase).orElse(null);
        if (user == null) return Optional.empty();
        UserInfoDTO userInfoDTO = new UserInfoDTO(user);
        return Optional.of(userInfoDTO);
    }
}
