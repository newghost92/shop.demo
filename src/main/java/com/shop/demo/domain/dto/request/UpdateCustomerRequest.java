package com.shop.demo.domain.dto.request;

import com.shop.demo.config.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class UpdateCustomerRequest {

    @JsonIgnore
    private Long id;

    @Length(max = 250)
    private String name;

    @Length(max = 500)
    private String address;

    @Length(max = 250)
    @Email(regexp = Constants.EMAIL_REGEX_ACCEPT_BLANK)
    private String email;

    @Length(max = 20)
    @Pattern(regexp = Constants.NUMBER_REGEX, message = "{InvalidPhoneNumber}")
    private String tel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "UpdateCustomerRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
