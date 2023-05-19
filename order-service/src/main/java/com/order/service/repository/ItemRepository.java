package com.order.service.repository;

import com.order.service.entities.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item,Long> {

    Boolean existsByName(String name);
}
