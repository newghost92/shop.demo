package com.shop.demo.domain.dto.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.http.HttpStatus;

public class WrapRestResponse implements Serializable {

    /**
     * Reason phrase of status code
     */
    private int status;
    private String message;

    /**
     * key: field name, value: message invalid
     */
    private Map<String, ?> fieldErrors;

    private Object result;

    /**
     * Constructor
     */
    public WrapRestResponse() {
        this.status = HttpStatus.OK.value();
        this.result = new ResponseData("success");
    }

    public WrapRestResponse(Object result) {
        this.status = HttpStatus.OK.value();
        this.result = result;
    }

    public static WrapRestResponse returnSearchEmptyResult(
        Integer totalPages, Integer currentPage, Integer rowsPerPage, Long totalElements
    ) {
        return new WrapRestResponse(new PageResponse<>(totalPages, currentPage, rowsPerPage, totalElements, new ArrayList<>()));
    }

    public WrapRestResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public WrapRestResponse(int status, String message, Map<String, ?> fieldErrors) {
        this.status = status;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

    public static WrapRestResponse success(Object result) {
        return new WrapRestResponse(result);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, ?> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "WrapRestResponse{" +
            "status=" + status +
            ", message='" + message + '\'' +
            ", fieldErrors=" + fieldErrors +
            ", result=" + result +
            '}';
    }
}
