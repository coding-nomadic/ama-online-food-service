package com.order.service.controllers;


import com.order.service.models.MenuDto;
import com.order.service.models.MenuDtoResponse;
import com.order.service.services.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/items")
public class MenuController {

    private MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<MenuDtoResponse> createMenus(@RequestBody MenuDto menuDto) {
        return ResponseEntity.ok(menuService.createMenu(menuDto));
    }

    @GetMapping
    public ResponseEntity<List<MenuDtoResponse>> fetchMenus() {
        return ResponseEntity.ok(menuService.fetchMenu());
    }
}
