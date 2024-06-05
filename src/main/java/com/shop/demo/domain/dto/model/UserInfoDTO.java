package com.shop.demo.domain.dto.model;

import com.shop.demo.domain.entities.User;
import java.io.Serializable;
import org.springframework.context.i18n.LocaleContextHolder;

public class UserInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private String username;

    private String name;

    private String status;

    public UserInfoDTO() {
    }

    public UserInfoDTO(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.status = user.getStatus().getNameByLang(LocaleContextHolder.getLocale().getLanguage());
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserInfoDTO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
