package com.shop.demo.service;

import com.shop.demo.domain.entities.User;
import com.shop.demo.enums.ActionType;
import com.shop.demo.config.securityConfig.jwt.JWTToken;
import com.shop.demo.domain.dto.request.Base64PasswordDecodeRequest;
import com.shop.demo.domain.dto.request.LoginRequest;
import org.springframework.http.HttpHeaders;

public interface AuthService {

    JWTToken login(LoginRequest loginRequest);

    void logout();

    void clearUserCaches(String username);

    HttpHeaders prepareHeader(String token);

    User getCurrentLoginUser();

    Base64PasswordDecodeRequest validateRequest(String password, String newPassword, ActionType actionType);
}
