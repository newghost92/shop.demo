package com.shop.demo.service;

import com.shop.demo.domain.entities.User;
import com.shop.demo.domain.dto.model.UserInfoDTO;
import com.shop.demo.domain.dto.request.PasswordChangeRequest;
import java.util.Optional;

public interface UserService {

    void changePasswordForCurrentUser(PasswordChangeRequest passwordChangeRequest);

    void changePassword(User user, String newPassword);

    Optional<UserInfoDTO> getCurrentUserInfo();
}
