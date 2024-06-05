package com.shop.demo.domain.entities;

import com.shop.demo.utils.NumberUtil;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "storages")
@EntityListeners(AuditingEntityListener.class)
public class Storage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    @NotNull
    @Column(name = "inventory")
    private Long inventory = 0L;

    @Column(name = "locked")
    private Long locked = 0L;

    @Column(name = "sold")
    private Long sold = 0L;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    public void lockItem(Long number) {
        if (NumberUtil.compareSmaller(inventory, number)) return;
        this.inventory -= number;
        this.locked += number;
    }

    public void unlockItem(Long number) {
        if (NumberUtil.compareSmaller(number, 0L)) return;
        this.inventory += number;
        this.locked -= number;
        this.locked = this.locked >= 0 ? this.locked : 0L;
    }

    public void soldItem(Long number) {
        if (NumberUtil.compareSmaller(locked, number)) return;
        this.locked -= number;
        this.sold += number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Long getInventory() {
        return inventory;
    }

    public void setInventory(Long inventory) {
        this.inventory = inventory;
    }

    public Long getLocked() {
        return locked;
    }

    public void setLocked(Long locked) {
        this.locked = locked;
    }

    public Long getSold() {
        return sold;
    }

    public void setSold(Long sold) {
        this.sold = sold;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", item=" + item +
                ", inventory=" + inventory +
                ", locked=" + locked +
                ", sold=" + sold +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
