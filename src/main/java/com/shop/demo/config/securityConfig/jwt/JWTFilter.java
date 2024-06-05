package com.shop.demo.config.securityConfig.jwt;

import com.shop.demo.config.Constants;
import com.shop.demo.config.securityConfig.tokenStorage.RedisTokenCache;
import com.shop.demo.domain.dto.response.WrapRestResponse;
import com.shop.demo.domain.entities.User;
import com.shop.demo.enums.Status;
import com.shop.demo.repository.UserRepository;
import com.shop.demo.utils.CustomMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a valid user is
 * found.
 */
public class JWTFilter extends GenericFilterBean {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
    public static final String ERROR_PATH = "/error";

    public static final String HEADER_ACCEPT_LANGUAGE = "Accept-Language";
    public static final String ACCEPT_LANGUAGE_DEFAULT = "en";

    private final TokenProvider tokenProvider;
    private final RedisTokenCache tokenCache;
    private final CustomMessage messageResource;

    private final Environment env;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public JWTFilter(TokenProvider tokenProvider,
                     RedisTokenCache tokenCache,
                     CustomMessage messageResource,
                     Environment env,
                     UserRepository userRepository,
                     ObjectMapper objectMapper) {
        this.tokenProvider = tokenProvider;
        this.tokenCache = tokenCache;
        this.messageResource = messageResource;
        this.env = env;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = prepareHeader(servletRequest);
        HttpServletResponse response = prepareResponseHeader(servletResponse);

        String contextPath = StringUtils.hasText(httpServletRequest.getServletPath())
            ? httpServletRequest.getServletPath()
            : httpServletRequest.getPathInfo();

        String jwt = resolveToken(httpServletRequest);
        if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt) && !Objects.equals(contextPath, ERROR_PATH)) {
            boolean hasStored = tokenCache.isTokenStored(jwt);

            if (!hasStored) {
                String errorMsg = messageResource.get(Constants.ErrorCode.E0005);
                prepareError(response, errorMsg, HttpStatus.PRECONDITION_FAILED);
                return;
            }

            Claims claims = this.tokenProvider.parseToken(jwt);
            String username = claims.get("sub", String.class);
            User currentUser = userRepository.findOneByUsername(username).orElse(null);

            if (currentUser == null || currentUser.getStatus() == Status.INACTIVATE) {
                String errorMsg = messageResource.get(Constants.ErrorCode.E0003, username);
                prepareError(response, errorMsg, HttpStatus.PRECONDITION_FAILED);
                return;
            }

            Authentication authentication = this.tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(servletRequest, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void prepareError(ServletResponse servletResponse, String errorMsg, HttpStatus status) throws IOException {
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        res.setStatus(status.value());
        res.setContentType(CONTENT_TYPE);
        res.setCharacterEncoding("UTF-8");
        WrapRestResponse response = new WrapRestResponse(status.value(), errorMsg);
        res.getWriter().write(objectMapper.writeValueAsString(response));
    }

    private HttpServletRequest prepareHeader(ServletRequest request) {
        ServletRequestWrapper httpServletRequest = new ServletRequestWrapper((HttpServletRequest) request);
        String acceptLang = httpServletRequest.getHeader(HEADER_ACCEPT_LANGUAGE);
        if (acceptLang == null) {
            httpServletRequest.addHeader(HEADER_ACCEPT_LANGUAGE, ACCEPT_LANGUAGE_DEFAULT);
        }

        return httpServletRequest;
    }

    private HttpServletResponse prepareResponseHeader(ServletResponse servletResponse) {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String allowOrigin = env.getProperty("app.cors.allowed_origins");
        String allowCredentials = env.getProperty("app.cors.allow_credentials");
        String allowedMethods = env.getProperty("app.cors.allowed_methods");
        String allowedHeaders = env.getProperty("app.cors.allowed_headers");
        String maxAge = env.getProperty("app.cors.max-age");

        response.setHeader("Access-Control-Allow-Origin", allowOrigin);
        response.setHeader("Access-Control-Allow-Credentials", allowCredentials);
        response.setHeader("Access-Control-Allow-Methods", allowedMethods);
        response.setHeader("Access-Control-Allow-Headers", allowedHeaders);
        response.setHeader("Access-Control-Max-Age", maxAge);

        return response;
    }
}
