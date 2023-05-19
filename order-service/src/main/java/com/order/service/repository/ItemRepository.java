package com.order.service.repository;

import com.order.service.entities.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface ItemRepository extends CrudRepository<Item,Long> {

    Optional<Item> findByName(String name);
}
