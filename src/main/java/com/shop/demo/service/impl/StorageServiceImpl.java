package com.shop.demo.service.impl;

import com.shop.demo.config.Constants;
import com.shop.demo.domain.entities.Item;
import com.shop.demo.domain.entities.Storage;
import com.shop.demo.exceptions.ValidationException;
import com.shop.demo.repository.StorageRepository;
import com.shop.demo.service.StorageService;
import com.shop.demo.utils.CustomMessage;
import com.shop.demo.utils.NumberUtil;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StorageServiceImpl implements StorageService {

    private final StorageRepository storageRepository;
    private final CustomMessage customMessage;

    public StorageServiceImpl(
            StorageRepository storageRepository,
            CustomMessage customMessage
    ) {
        this.storageRepository = storageRepository;
        this.customMessage = customMessage;
    }

    @Override
    public void saveInventoryOfItem(Item item, Long number) {
        if (item == null || item.getId() == null || NumberUtil.compareSmaller(number, 0L)) return;
        Storage storage = storageRepository.findByItem(item.getId()).orElse(new Storage());
        storage.setItem(item);
        storage.setInventory(number);
        storageRepository.save(storage);
    }

    @Override
    public void lockItemInStorage(Item item, Long number) {
        if (item == null || item.getStorage() == null || NumberUtil.compareSmaller(number, 0L)) return;
        Storage storage = item.getStorage();
        if (NumberUtil.compareSmaller(storage.getInventory(), number))
            throw new ValidationException(Constants.Label.STORAGE, customMessage.get(Constants.ErrorCode.E0012, Constants.Label.ITEM));
        storage.lockItem(number);
        storageRepository.save(storage);
    }

    @Override
    @Transactional
    public void lockItemInStorage(Map<Item, Integer> cart) {
        for (Map.Entry<Item, Integer> e : cart.entrySet()) {
            lockItemInStorage(e.getKey(), Long.valueOf(e.getValue()));
        }
    }

    @Override
    public void soldItem(Item item, Long number) {
        if (item == null || item.getStorage() == null || NumberUtil.compareSmaller(number, 0L)) return;
        Storage storage = item.getStorage();
        storage.soldItem(number);
        storageRepository.save(storage);
    }

    @Override
    @Transactional
    public void soldItem(Map<Item, Integer> cart) {
        for (Map.Entry<Item, Integer> e : cart.entrySet()) {
            soldItem(e.getKey(), Long.valueOf(e.getValue()));
        }
    }

    @Override
    public void unlockItemInStorage(Item item, Long number) {
        if (item == null || item.getStorage() == null) return;
        Storage storage = item.getStorage();
        storage.unlockItem(number);
        storageRepository.save(storage);
    }

    @Override
    @Transactional
    public void unLockItemInStorage(Map<Item, Integer> cart) {
        for (Map.Entry<Item, Integer> e : cart.entrySet()) {
            unlockItemInStorage(e.getKey(), Long.valueOf(e.getValue()));
        }
    }
}
