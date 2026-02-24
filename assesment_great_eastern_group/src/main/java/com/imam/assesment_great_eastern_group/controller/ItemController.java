package com.imam.assesment_great_eastern_group.controller;

import com.imam.assesment_great_eastern_group.dto.CreateItemRequest;
import com.imam.assesment_great_eastern_group.entity.Item;
import com.imam.assesment_great_eastern_group.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public Item createItem(@RequestBody @Valid CreateItemRequest request) {
        return itemService.createItem(request.getName());
    }

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
    }
}
