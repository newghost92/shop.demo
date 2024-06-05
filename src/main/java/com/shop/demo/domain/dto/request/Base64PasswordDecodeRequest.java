package com.shop.demo.domain.dto.request;

import com.shop.demo.config.Constants;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class Base64PasswordDecodeRequest {
    private String password;
    @Length(min = 8, message = "{MethodArgumentNotValidException.newPassword}")
    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "{MethodArgumentNotValidException.newPassword}")
    private String newPassword;

    public Base64PasswordDecodeRequest(String password, String newPassword) {
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
