package com.imam.assesment_great_eastern_group.repository;

import com.imam.assesment_great_eastern_group.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    boolean existsByName(String name);
}