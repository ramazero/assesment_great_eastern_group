package com.imam.assesment_great_eastern_group.service;

import com.imam.assesment_great_eastern_group.dto.ItemResponse;
import com.imam.assesment_great_eastern_group.entity.Item;
import com.imam.assesment_great_eastern_group.repository.ItemRepository;
import com.imam.assesment_great_eastern_group.repository.VariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final VariantRepository variantRepository;

    @Transactional
    public ItemResponse createItem(String name) {

        if (itemRepository.existsByName(name)) {
            throw new RuntimeException("Item already exists");
        }

        Item item = Item.builder()
                .name(name)
                .build();

        Item saved = itemRepository.save(item);

        return new ItemResponse(
                saved.getId(),
                saved.getName(),
                saved.getCreatedAt()
        );
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

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (variantRepository.existsByItemId(id)) {
            throw new RuntimeException("Cannot delete item with existing variants");
        }

        itemRepository.delete(item);
    }
}