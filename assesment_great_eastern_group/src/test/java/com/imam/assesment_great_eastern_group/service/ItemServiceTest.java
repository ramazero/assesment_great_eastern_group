package com.imam.assesment_great_eastern_group.service;

import com.imam.assesment_great_eastern_group.dto.ItemResponse;
import com.imam.assesment_great_eastern_group.entity.Item;
import com.imam.assesment_great_eastern_group.repository.ItemRepository;
import com.imam.assesment_great_eastern_group.repository.VariantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private VariantRepository variantRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    void shouldCreateItem() {

        Item item = new Item();
        item.setId(1L);
        item.setName("T-Shirt");

        when(itemRepository.existsByName("T-Shirt")).thenReturn(false);
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        ItemResponse response = itemService.createItem("T-Shirt");

        assertEquals("T-Shirt", response.getName());
    }

    @Test
    void shouldThrowWhenItemExists() {

        when(itemRepository.existsByName("T-Shirt")).thenReturn(true);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> itemService.createItem("T-Shirt")
        );

        assertEquals("Item already exists", ex.getMessage());
    }
}
