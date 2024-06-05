package com.shop.demo.domain.dto.response;

import com.shop.demo.domain.entities.Customer;
import com.shop.demo.repository.mapper.I_Order;

public class CustomerDetail {

    private Long id;
    private String name;
    private String address;
    private String email;
    private String tel;

    public CustomerDetail(Customer customer) {
        if (customer != null) {
            this.id = customer.getId();
            this.name = customer.getName();
            this.address = customer.getAddress();
            this.email = customer.getEmail();
            this.tel = customer.getTelephoneNumber();
        }
    }

    public CustomerDetail(I_Order io) {
        if (io != null) {
            this.id = io.getCustomerId();
            this.name = io.getCustomerName();
            this.address = io.getCustomerAddress();
            this.email = io.getCustomerEmail();
            this.tel = io.getCustomerTel();
        }
    }

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
        return "CustomerDetail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
