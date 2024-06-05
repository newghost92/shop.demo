package com.shop.demo.service.impl;

import com.shop.demo.config.Constants;
import com.shop.demo.domain.dto.request.CreateItemRequest;
import com.shop.demo.domain.dto.request.SearchItemRequest;
import com.shop.demo.domain.dto.request.UpdateItemRequest;
import com.shop.demo.domain.dto.response.ItemDetail;
import com.shop.demo.domain.dto.response.PageResponse;
import com.shop.demo.domain.entities.Item;
import com.shop.demo.enums.Status;
import com.shop.demo.exceptions.ItemExistedException;
import com.shop.demo.exceptions.ValidationException;
import com.shop.demo.repository.ItemRepository;
import com.shop.demo.service.ItemService;
import com.shop.demo.service.StorageService;
import com.shop.demo.utils.CustomMessage;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final StorageService storageService;
    private final CustomMessage customMessage;

    public ItemServiceImpl(
            ItemRepository itemRepository,
            StorageService storageService,
            CustomMessage customMessage
    ) {
        this.itemRepository = itemRepository;
        this.storageService = storageService;
        this.customMessage = customMessage;
    }

    @Override
    @Transactional
    public void createItem(@Valid @NotNull(message = "api.request.invalid") CreateItemRequest request) {
        itemRepository.findByName(request.getName())
                .ifPresent(item -> { throw new ItemExistedException(item.getName());});
        Item item = new Item();
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());
        item = itemRepository.save(item);

        storageService.saveInventoryOfItem(item, request.getNumber());
    }

    @Override
    public ItemDetail getDetail(@NotNull Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ValidationException(Constants.Label.ITEM_ID, customMessage.get(Constants.ErrorCode.E0002, Constants.Label.ITEM)));
        return new ItemDetail(item);
    }

    @Override
    public void updateItem(@Valid @NotNull(message = "api.request.invalid") UpdateItemRequest request) {
        if (request.getId() == null) throw new IllegalArgumentException();
        Item item = itemRepository.findById(request.getId())
                .orElseThrow(() -> new ValidationException(Constants.Label.ITEM_ID, customMessage.get(Constants.ErrorCode.E0001, Constants.Label.ITEM)));
        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());
        itemRepository.save(item);
    }

    @Override
    public void activeItem(@NotNull Long id) {
        itemRepository.updateItemStatus(id, Status.ACTIVATE.getValue());
    }

    @Override
    public void inactiveItem(@NotNull Long id) {
        itemRepository.updateItemStatus(id, Status.INACTIVATE.getValue());
    }

    @Override
    public PageResponse<ItemDetail> search(SearchItemRequest request) {
        PageRequest pageable = PageRequest.of(request.getRealCurrentPage(), request.getRowsPerPage());

        return PageResponse.of(
                itemRepository.search(
                        request.getName(),
                        request.getDescription(),
                        request.getStatus(),
                        pageable
                ).map(ItemDetail::new)
        );
    }
}
