package com.order.service.services;

import com.order.service.entities.Item;
import com.order.service.models.ItemDto;
import com.order.service.models.ItemDtoResponse;
import com.order.service.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    private ModelMapper modelMapper;

    public ItemService(ItemRepository itemRepository, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.itemRepository = itemRepository;
    }

    public ItemDtoResponse createItems(ItemDto itemDto) {
        Item itemResponse = itemRepository.save(modelMapper.map(itemDto, Item.class));
        return modelMapper.map(itemResponse, ItemDtoResponse.class);
    }

    public List<ItemDtoResponse> fetchItems() {
        final Iterable<Item> items = itemRepository.findAll();
        return StreamSupport.stream(items.spliterator(), false).map(m -> modelMapper.map(m, ItemDtoResponse.class)).collect(Collectors.toList());
    }
}
