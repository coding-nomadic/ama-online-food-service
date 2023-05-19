package com.order.service.controllers;


import com.order.service.models.ItemDto;
import com.order.service.models.ItemDtoResponse;
import com.order.service.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDtoResponse> createItems(@RequestBody ItemDto itemDto) {
        return ResponseEntity.ok(itemService.createItems(itemDto));
    }

    @GetMapping
    public ResponseEntity<List<ItemDtoResponse>> fetchItems() {
        return ResponseEntity.ok(itemService.fetchItems());
    }
}
