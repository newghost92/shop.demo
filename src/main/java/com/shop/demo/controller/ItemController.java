package com.shop.demo.controller;

import com.shop.demo.domain.dto.request.CreateItemRequest;
import com.shop.demo.domain.dto.request.SearchItemRequest;
import com.shop.demo.domain.dto.request.UpdateItemRequest;
import com.shop.demo.domain.dto.response.WrapRestResponse;
import com.shop.demo.service.ItemService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(
            ItemService itemService
    ) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<WrapRestResponse> create(@Valid @ModelAttribute CreateItemRequest request) {
        itemService.createItem(request);
        return ResponseEntity.ok(new WrapRestResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WrapRestResponse> getDetail(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new WrapRestResponse(itemService.getDetail(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WrapRestResponse> update(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute UpdateItemRequest request
    ) {
        request.setId(id);
        itemService.updateItem(request);
        return ResponseEntity.ok(new WrapRestResponse());
    }

    @PutMapping("/{id}/active")
    public ResponseEntity<WrapRestResponse> active(@PathVariable("id") Long id) {
        itemService.activeItem(id);
        return ResponseEntity.ok(new WrapRestResponse());
    }

    @PutMapping("/{id}/inactive")
    public ResponseEntity<WrapRestResponse> inactive(@PathVariable("id") Long id) {
        itemService.inactiveItem(id);
        return ResponseEntity.ok(new WrapRestResponse());
    }

    @PostMapping("/search")
    public ResponseEntity<WrapRestResponse> search(@Valid @RequestBody SearchItemRequest request) {
        return ResponseEntity.ok(new WrapRestResponse(itemService.search(request)));
    }
}
