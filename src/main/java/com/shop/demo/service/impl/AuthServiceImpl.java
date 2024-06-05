package com.shop.demo.service.impl;

import com.shop.demo.config.Constants;
import com.shop.demo.domain.entities.User;
import com.shop.demo.enums.ActionType;
import com.shop.demo.enums.Status;
import com.shop.demo.exceptions.BadRequestException;
import com.shop.demo.exceptions.CombinedValidationException;
import com.shop.demo.exceptions.UserNotFoundException;
import com.shop.demo.repository.UserRepository;
import com.shop.demo.config.securityConfig.SecurityUtils;
import com.shop.demo.config.securityConfig.jwt.JWTFilter;
import com.shop.demo.config.securityConfig.jwt.JWTToken;
import com.shop.demo.config.securityConfig.jwt.TokenProvider;
import com.shop.demo.config.securityConfig.tokenStorage.RedisTokenCache;
import com.shop.demo.domain.dto.request.Base64PasswordDecodeRequest;
import com.shop.demo.domain.dto.request.LoginRequest;
import com.shop.demo.service.AuthService;
import com.shop.demo.utils.CustomMessage;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Value("${password.key}")
    private String passwordKey;

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserRepository userRepository;
    private final RedisTokenCache tokenCache;
    private final Validator validator;
    private final CustomMessage messageResource;

    public AuthServiceImpl(TokenProvider tokenProvider,
                           AuthenticationManagerBuilder authenticationManagerBuilder,
                           UserRepository userRepository,
                           RedisTokenCache tokenCache, Validator validator, CustomMessage messageResource) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userRepository = userRepository;
        this.tokenCache = tokenCache;
        this.validator = validator;
        this.messageResource = messageResource;
    }

    @Override
    public JWTToken login(LoginRequest loginRequest) {
        validate(loginRequest);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginRequest.getUsername(),
            loginRequest.getPassword()
        );
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        tokenCache.storeToken(authentication.getName(), jwt);

        return new JWTToken(jwt);
    }

    @Override
    public void logout() {
        SecurityUtils.getCurrentUserJWT().ifPresent(tokenCache::deleteToken);
        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContextHolder.clearContext();
    }

    @Override
    public void clearUserCaches(String username) {
        tokenCache.deleteTokenForUser(username);
    }

    @Override
    public HttpHeaders prepareHeader(String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + token);

        return httpHeaders;
    }

    @Override
    public User getCurrentLoginUser() {
        String username = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(UserNotFoundException::new);

        return userRepository.findOneByUsernameIgnoreCase(username)
            .orElseThrow(UserNotFoundException::new);
    }

    public Base64PasswordDecodeRequest validateRequest(String password, String newPassword, ActionType actionType) {
        Base64PasswordDecodeRequest base64PasswordDecodeRequest;
        try {
            base64PasswordDecodeRequest = decryptPassword(password, newPassword, actionType);
        } catch (Exception e) {
            log.info("[ERROR] CHANGE_PASSWORD: ");
            e.printStackTrace();
            throw new BadRequestException(messageResource.get(Constants.ErrorCode.E0002));
        }
        if (actionType == ActionType.CHANGE_PASSWORD) {
            Map<String, List<String>> reasonFailMap = new LinkedHashMap<>();
            var errorSet = validator.validate(base64PasswordDecodeRequest)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());
            if (!CollectionUtils.isEmpty(errorSet)) {
                for (String message : errorSet) {
                    messageResource.addMessage(reasonFailMap, Constants.Label.NEW_PASSWORD, message);
                }
            }
            if (!reasonFailMap.isEmpty())
                throw new CombinedValidationException(messageResource.get(Constants.ErrorCode.E0007), reasonFailMap);
        }
        return base64PasswordDecodeRequest;
    }

    private Base64PasswordDecodeRequest decryptPassword(String password, String newPassword, ActionType actionType) throws
        NoSuchAlgorithmException,
        NoSuchPaddingException,
        InvalidKeyException,
        IllegalBlockSizeException,
        BadPaddingException
    {
        var convertPass = "";
        var convertNewPass = "";
        byte[] key = passwordKey.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        if (actionType == ActionType.CHANGE_PASSWORD) {
            convertNewPass = new String(cipher.doFinal(Base64.getDecoder().decode(newPassword)));
        }
        convertPass = new String(cipher.doFinal(Base64.getDecoder().decode(password)));
        return new Base64PasswordDecodeRequest(convertPass, convertNewPass);
    }

    private void validate(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findOneByUsernameIgnoreCase(loginRequest.getUsername());
        if (user.isPresent() && user.get().getStatus().equals(Status.INACTIVATE)) {
            throw new BadRequestException(messageResource.get(Constants.ErrorCode.E0006));
        }
    }
}
