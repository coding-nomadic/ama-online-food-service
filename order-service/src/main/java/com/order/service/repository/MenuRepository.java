package com.order.service.repository;

import com.order.service.entities.Menu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends CrudRepository<Menu, Long> {

    Boolean existsByName(String name);
}
