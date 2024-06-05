package com.shop.demo.domain.dto.request;

public class SearchOrderRequest extends BaseSearchRequestDTO {

    private Long id;
    private String customerName;
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SearchOrderRequest{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", status=" + status +
                '}';
    }
}
