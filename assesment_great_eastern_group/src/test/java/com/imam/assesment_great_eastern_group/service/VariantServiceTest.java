package com.imam.assesment_great_eastern_group.service;

import com.imam.assesment_great_eastern_group.dto.CreateVariantRequest;
import com.imam.assesment_great_eastern_group.dto.VariantResponse;
import com.imam.assesment_great_eastern_group.entity.Item;
import com.imam.assesment_great_eastern_group.entity.Variant;
import com.imam.assesment_great_eastern_group.repository.ItemRepository;
import com.imam.assesment_great_eastern_group.repository.VariantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VariantServiceTest {

    @Mock
    private VariantRepository variantRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private VariantService variantService;

    @Test
    void shouldIncreaseStockWhenVariantAlreadyExists() {

        CreateVariantRequest request = new CreateVariantRequest(
                1L,
                "Size L Black",
                new BigDecimal("150000"),
                5
        );

        Item item = new Item();
        item.setId(1L);
        item.setName("T-Shirt");

        Variant existing = new Variant();
        existing.setId(10L);
        existing.setName("Size L Black");
        existing.setPrice(new BigDecimal("150000"));
        existing.setStock(10);
        existing.setItem(item);

        when(variantRepository.findByItemIdAndName(1L, "Size L Black"))
                .thenReturn(Optional.of(existing));

        VariantResponse response = variantService.createVariant(request);

        assertEquals(15, response.getStock());
        assertEquals("Size L Black", response.getName());

        verify(variantRepository, never()).save(any());
    }

    @Test
    void shouldCreateNewVariantWhenNotExists() {

        CreateVariantRequest request = new CreateVariantRequest(
                1L,
                "Size M Black",
                new BigDecimal("120000"),
                10
        );

        Item item = new Item();
        item.setId(1L);
        item.setName("T-Shirt");

        when(variantRepository.findByItemIdAndName(1L, "Size M Black"))
                .thenReturn(Optional.empty());

        when(itemRepository.findById(1L))
                .thenReturn(Optional.of(item));

        Variant saved = new Variant();
        saved.setId(20L);
        saved.setName("Size M Black");
        saved.setPrice(new BigDecimal("120000"));
        saved.setStock(10);
        saved.setItem(item);

        when(variantRepository.save(any(Variant.class)))
                .thenReturn(saved);

        VariantResponse response = variantService.createVariant(request);

        assertEquals(10, response.getStock());
        assertEquals("Size M Black", response.getName());

        verify(variantRepository, times(1)).save(any());
    }

    @Test
    void shouldThrowExceptionWhenItemNotFound() {

        CreateVariantRequest request = new CreateVariantRequest(
                99L,
                "Size XL",
                new BigDecimal("200000"),
                5
        );

        when(variantRepository.findByItemIdAndName(99L, "Size XL"))
                .thenReturn(Optional.empty());

        when(itemRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> variantService.createVariant(request)
        );

        assertEquals("Item not found", exception.getMessage());
    }
}
