package com.shop.demo.config.securityConfig.jwt;

import com.shop.demo.config.securityConfig.tokenStorage.RedisTokenCache;
import com.shop.demo.repository.UserRepository;
import com.shop.demo.utils.CustomMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;
    private final RedisTokenCache tokenCache;
    private final Environment env;
    private final UserRepository userRepository;
    private final CustomMessage messageResource;
    private final ObjectMapper objectMapper;

    public JWTConfigurer(TokenProvider tokenProvider,
                         RedisTokenCache tokenCache,
                         Environment env,
                         UserRepository userRepository,
                         CustomMessage messageResource,
                         ObjectMapper objectMapper
    ) {
        this.tokenProvider = tokenProvider;
        this.tokenCache = tokenCache;
        this.env = env;
        this.userRepository = userRepository;
        this.messageResource = messageResource;
        this.objectMapper = objectMapper;
    }

    @Override
    public void configure(HttpSecurity http) {
        JWTFilter customFilter = new JWTFilter(
            tokenProvider,
            tokenCache,
            messageResource,
            env,
            userRepository,
            objectMapper
        );
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
