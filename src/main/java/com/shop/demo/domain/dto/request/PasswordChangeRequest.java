package com.shop.demo.domain.dto.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

public class PasswordChangeRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String currentPassword;

    public PasswordChangeRequest() {
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
}
