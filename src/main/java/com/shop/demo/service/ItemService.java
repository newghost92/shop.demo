package com.shop.demo.service;

import com.shop.demo.domain.dto.request.CreateItemRequest;
import com.shop.demo.domain.dto.request.SearchItemRequest;
import com.shop.demo.domain.dto.request.UpdateItemRequest;
import com.shop.demo.domain.dto.response.ItemDetail;
import com.shop.demo.domain.dto.response.PageResponse;

public interface ItemService {

    void createItem(CreateItemRequest request);

    ItemDetail getDetail(Long id);

    void updateItem(UpdateItemRequest request);

    void activeItem(Long id);

    void inactiveItem(Long id);

    PageResponse<ItemDetail> search(SearchItemRequest request);
}
