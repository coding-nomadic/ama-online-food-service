package com.order.service.controllers;


import com.order.service.models.MenuDto;
import com.order.service.models.MenuDtoResponse;
import com.order.service.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/items")
public class MenuController {

    private MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<MenuDtoResponse> createMenus(@RequestBody MenuDto menuDto) {
        return ResponseEntity.ok(menuService.createMenu(menuDto));
    }

    @GetMapping
    public ResponseEntity<List<MenuDtoResponse>> fetchMenus() {
        return ResponseEntity.ok(menuService.fetchMenus());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<MenuDtoResponse> fetchById(@PathVariable String id) {
        return ResponseEntity.ok(menuService.fetchMenu(id));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        menuService.deleteMenu(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
