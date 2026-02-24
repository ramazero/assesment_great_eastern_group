package com.imam.assesment_great_eastern_group.service;

import com.imam.assesment_great_eastern_group.entity.Item;
import com.imam.assesment_great_eastern_group.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Item createItem(String name) {
        if (itemRepository.existsByName(name)) {
            throw new RuntimeException("Item already exists");
        }

        Item item = Item.builder()
                .name(name)
                .build();

        return itemRepository.save(item);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    @Transactional
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}