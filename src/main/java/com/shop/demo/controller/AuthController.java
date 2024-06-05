package com.shop.demo.controller;

import com.shop.demo.config.securityConfig.jwt.JWTToken;
import com.shop.demo.domain.dto.model.UserInfoDTO;
import com.shop.demo.domain.dto.request.Base64PasswordDecodeRequest;
import com.shop.demo.domain.dto.request.LoginRequest;
import com.shop.demo.domain.dto.request.PasswordChangeRequest;
import com.shop.demo.domain.dto.response.WrapRestResponse;
import com.shop.demo.enums.ActionType;
import com.shop.demo.exceptions.UserNotFoundException;
import com.shop.demo.service.AuthService;
import com.shop.demo.service.impl.UserServiceImpl;
import javax.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserServiceImpl userService;
    private final AuthService accountService;

    public AuthController(UserServiceImpl userService,
                          AuthService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @PostMapping("/login")
    public ResponseEntity<WrapRestResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Base64PasswordDecodeRequest convertPass = accountService.validateRequest(loginRequest.getPassword(), null, ActionType.LOGIN);
        loginRequest.setPassword(convertPass.getPassword());
        JWTToken jwt = accountService.login(loginRequest);
        HttpHeaders httpHeaders = accountService.prepareHeader(jwt.getIdToken());
        WrapRestResponse response = new WrapRestResponse(jwt);
        return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<WrapRestResponse> logout() {
        accountService.logout();
        return ResponseEntity.ok(new WrapRestResponse());
    }

    @GetMapping("/user-info")
    public ResponseEntity<WrapRestResponse> getCurrentLoginInfo() {
        UserInfoDTO userInfoDTO = userService.getCurrentUserInfo()
            .orElseThrow(UserNotFoundException::new);
        return ResponseEntity.ok(WrapRestResponse.success(userInfoDTO));
    }

    @PutMapping("/change-password")
    public ResponseEntity<WrapRestResponse> changePassword(
        @Valid @RequestBody PasswordChangeRequest passwordChangeRequest
    ) {
        Base64PasswordDecodeRequest convertPass = accountService.validateRequest(passwordChangeRequest.getCurrentPassword(), passwordChangeRequest.getNewPassword(), ActionType.CHANGE_PASSWORD);
        passwordChangeRequest.setCurrentPassword(convertPass.getPassword());
        passwordChangeRequest.setNewPassword(convertPass.getNewPassword());
        userService.changePasswordForCurrentUser(passwordChangeRequest);
        return ResponseEntity.ok(new WrapRestResponse());
    }
}
