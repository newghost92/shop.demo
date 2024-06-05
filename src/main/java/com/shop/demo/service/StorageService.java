package com.shop.demo.service;

import com.shop.demo.domain.entities.Item;
import java.util.Map;

public interface StorageService {

    void saveInventoryOfItem(Item item, Long number);

    void lockItemInStorage(Item item, Long number);

    void lockItemInStorage(Map<Item, Integer> cart);

    void soldItem(Item item, Long number);

    void soldItem(Map<Item, Integer> cart);

    void unlockItemInStorage(Item item, Long number);

    void unLockItemInStorage(Map<Item, Integer> cart);
}
