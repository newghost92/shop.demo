package com.shop.demo.domain.dto.response;

import com.shop.demo.domain.entities.Item;
import com.shop.demo.enums.Status;
import com.shop.demo.repository.mapper.I_Item;
import java.math.BigDecimal;
import org.springframework.context.i18n.LocaleContextHolder;

public class ItemDetail {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String status;
    private Long inventory;

    public ItemDetail() {}

    public ItemDetail(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.status = item.getStatus().getNameByLang(LocaleContextHolder.getLocale().getLanguage());
        this.inventory = item.getStorage().getInventory();
    }

    public ItemDetail(I_Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.status = Status.get(item.getStatus()).getNameByLang(LocaleContextHolder.getLocale().getLanguage());
        this.inventory = item.getInventory();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getInventory() {
        return inventory;
    }

    public void setInventory(Long inventory) {
        this.inventory = inventory;
    }

    @Override
    public String toString() {
        return "ItemDetail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", inventory=" + inventory +
                '}';
    }
}
