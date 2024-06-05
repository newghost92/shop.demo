package com.shop.demo.domain.dto.response;

import java.io.Serializable;

public class ResponseData implements Serializable {
    private String status;

    public ResponseData(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
