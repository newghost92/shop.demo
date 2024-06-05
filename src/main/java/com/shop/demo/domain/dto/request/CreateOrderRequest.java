package com.shop.demo.domain.dto.request;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CreateOrderRequest {

    public static class Items {
        @NotNull
        private Long id;

        @NotNull
        @Min(0)
        private Integer number;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }
    }

    private Long customerId;
    private CreateCustomerRequest customerRequest;
    private Set<Items> items = new HashSet<>();

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public CreateCustomerRequest getCustomerRequest() {
        return customerRequest;
    }

    public void setCustomerRequest(CreateCustomerRequest customerRequest) {
        this.customerRequest = customerRequest;
    }

    public Set<Items> getItems() {
        return items;
    }

    public void setItems(Set<Items> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "CreateOrderRequest{" +
                "customerId=" + customerId +
                ", customerRequest=" + customerRequest +
                ", items=" + items +
                '}';
    }
}
