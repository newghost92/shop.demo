package com.shop.demo.config.securityConfig.jwt;

import com.shop.demo.utils.DateTimeUtil;
import com.shop.demo.config.securityConfig.CustomPrincipal;
import com.shop.demo.config.securityConfig.SecurityMetersService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TokenProvider {

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";
    private static final String INVALID_JWT_TOKEN = "Invalid JWT token.";
    private static final String CLAIMS_USER_ID = "user_id";

    private static final String SECRET_KEY = "app.security.authentication.jwt.base64-secret";

    private final Key key;

    private final JwtParser jwtParser;

    private final Environment env;
    private final SecurityMetersService securityMetersService;

    @Value("${time-zone.id}")
    private String defaultZoneId;

    public TokenProvider(Environment env, SecurityMetersService securityMetersService) {
        this.env = env;
        byte[] keyBytes;
        String base64Secret = env.getProperty(SECRET_KEY);
        keyBytes = Decoders.BASE64.decode(base64Secret);
        key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();

        this.securityMetersService = securityMetersService;
    }

    public String createToken(Authentication authentication) {
        Date expirationDate = setExpiration();
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();

        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        return Jwts.builder()
            .setIssuedAt(new Date())
            .setSubject(authentication.getName())
            .claim(CLAIMS_USER_ID, customPrincipal.getUserId())
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(expirationDate)
            .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        String username = claims.getSubject();
        Long userId = claims.get(CLAIMS_USER_ID, Long.class);
        Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
        if (StringUtils.hasText(claims.get(AUTHORITIES_KEY, String.class))) {
            authorities = Arrays.stream(claims.get(AUTHORITIES_KEY, String.class).split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        }

        CustomPrincipal customPrincipal = new CustomPrincipal(
            username,
            "",
            authorities,
            userId);

        return new UsernamePasswordAuthenticationToken(customPrincipal, token, authorities);
    }

    public Claims parseToken(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String authToken) {
        try {
            jwtParser.parseClaimsJws(authToken);

            return true;
        } catch (ExpiredJwtException e) {
            this.securityMetersService.trackTokenExpired();

            log.trace(INVALID_JWT_TOKEN, e);
        } catch (UnsupportedJwtException e) {
            this.securityMetersService.trackTokenUnsupported();

            log.trace(INVALID_JWT_TOKEN, e);
        } catch (MalformedJwtException e) {
            this.securityMetersService.trackTokenMalformed();

            log.trace(INVALID_JWT_TOKEN, e);
        } catch (SignatureException e) {
            this.securityMetersService.trackTokenInvalidSignature();

            log.trace(INVALID_JWT_TOKEN, e);
        } catch (
            IllegalArgumentException e) {
            log.error("Token validation error {}", e.getMessage());
        }
        return false;
    }

    /**
     * set expiration = last time of day
     *
     * @return expiration
     */
    private Date setExpiration() {
        LocalDate lcd = LocalDate.now();
        LocalTime lct = LocalTime.of(23, 59, 59, 999);
        LocalDateTime now = LocalDateTime.of(lcd, lct);
        return DateTimeUtil.convertLocalDateTimeToDate(now, ZoneId.of(defaultZoneId));
    }
}
