package com.shop.demo.config.securityConfig.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JWTToken {
    private String idToken;

    public JWTToken(String idToken) {
        this.idToken = idToken;
    }

    @JsonProperty("accessToken")
    public String getIdToken() {
        return idToken;
    }
}
