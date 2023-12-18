package com.order.service.services;


import com.order.service.constants.OrderConstants;
import com.order.service.entities.Menu;
import com.order.service.exceptions.ResourceNotFoundException;
import com.order.service.models.MenuDto;
import com.order.service.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MenuService {

    private MenuRepository menuRepository;

    private ModelMapper modelMapper;


    @Autowired
    public MenuService(MenuRepository menuRepository, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.menuRepository = menuRepository;
    }

    public Menu createMenu(MenuDto menuDto) {
        return menuRepository.save(modelMapper.map(menuDto, Menu.class));
    }

    public List<Menu> fetchMenus() {
        final Iterable<Menu> items = menuRepository.findAll();
        return StreamSupport.stream(items.spliterator(), false).collect(Collectors.toList());
    }

    public Menu fetchMenu(String id) {
        final Optional<Menu> items = menuRepository.findById(Long.valueOf(id));
        if (items.isPresent()) {
            return items.get();
        } else {
            throw new ResourceNotFoundException("Menu not found for this id : " + id, OrderConstants.MENU_NOT_FOUND_ERROR_CODE);
        }
    }

    public void deleteMenu(String id) {
        final Optional<Menu> items = menuRepository.findById(Long.valueOf(id));
        if (items.isPresent()) {
            menuRepository.deleteById(Long.valueOf(id));
        } else {
            throw new ResourceNotFoundException("Menu not found for this id : " + id, OrderConstants.MENU_NOT_FOUND_ERROR_CODE);
        }
    }
}
