package com.order.service.services;


import com.order.service.entities.Menu;
import com.order.service.exceptions.ResourceNotFoundException;
import com.order.service.models.MenuDto;
import com.order.service.models.MenuDtoResponse;
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

    public MenuDtoResponse createMenu(MenuDto menuDto) {
        Menu itemResponse = menuRepository.save(modelMapper.map(menuDto, Menu.class));
        return modelMapper.map(itemResponse, MenuDtoResponse.class);
    }

    public List<MenuDtoResponse> fetchMenus() {
        final Iterable<Menu> items = menuRepository.findAll();
        return StreamSupport.stream(items.spliterator(), false).map(m -> modelMapper.map(m, MenuDtoResponse.class)).collect(Collectors.toList());
    }

    public MenuDtoResponse fetchMenu(String id) {
        final Optional<Menu> items = menuRepository.findById(Long.valueOf(id));
        if (items.isPresent()) {
            return modelMapper.map(items.get(), MenuDtoResponse.class);
        } else {
            throw new ResourceNotFoundException("Menu not found for this id : " + id, "102");
        }
    }

    public void deleteMenu(String id) {
        final Optional<Menu> items = menuRepository.findById(Long.valueOf(id));
        if (items.isPresent()) {
            menuRepository.deleteById(Long.valueOf(id));
        } else {
            throw new ResourceNotFoundException("Menu not found for this id : " + id, "102");
        }
    }
}
