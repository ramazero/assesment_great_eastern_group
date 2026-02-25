package com.imam.assesment_great_eastern_group.service;

import com.imam.assesment_great_eastern_group.dto.SaleResponse;
import com.imam.assesment_great_eastern_group.dto.SellRequest;
import com.imam.assesment_great_eastern_group.entity.Sale;
import com.imam.assesment_great_eastern_group.entity.Variant;
import com.imam.assesment_great_eastern_group.repository.SaleRepository;
import com.imam.assesment_great_eastern_group.repository.VariantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaleServiceTest {

    @Mock
    private VariantRepository variantRepository;

    @Mock
    private SaleRepository saleRepository;

    @InjectMocks
    private SaleService saleService;

    @Test
    void shouldCreateSaleSuccessfully() {

        // Arrange
        SellRequest request = new SellRequest(1L, 3);

        Variant variant = new Variant();
        variant.setId(1L);
        variant.setName("Size L Black");
        variant.setStock(10);
        variant.setPrice(new BigDecimal("150000"));

        when(variantRepository.findById(1L))
                .thenReturn(Optional.of(variant));

        Sale savedSale = new Sale();
        savedSale.setId(100L);
        savedSale.setVariant(variant);
        savedSale.setQuantity(3);
        savedSale.setUnitPrice(new BigDecimal("150000"));
        savedSale.setTotalAmount(new BigDecimal("450000"));
        savedSale.setSoldAt(LocalDateTime.now());

        when(saleRepository.save(any(Sale.class)))
                .thenReturn(savedSale);

        // Act
        SaleResponse response = saleService.createSale(request);

        // Assert
        assertEquals(7, variant.getStock());
        assertEquals(new BigDecimal("450000"), response.getTotalAmount());
        assertEquals(3, response.getQuantity());
        assertEquals("Size L Black", response.getVariantName());

        verify(saleRepository, times(1)).save(any(Sale.class));
    }

    @Test
    void shouldThrowExceptionWhenVariantNotFound() {

        SellRequest request = new SellRequest(99L, 2);

        when(variantRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> saleService.createSale(request)
        );

        assertEquals("Variant not found", ex.getMessage());
        verify(saleRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenStockInsufficient() {

        SellRequest request = new SellRequest(1L, 5);

        Variant variant = new Variant();
        variant.setId(1L);
        variant.setStock(2);
        variant.setPrice(new BigDecimal("150000"));

        when(variantRepository.findById(1L))
                .thenReturn(Optional.of(variant));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> saleService.createSale(request)
        );

        assertEquals("Insufficient stock", ex.getMessage());
        verify(saleRepository, never()).save(any());
    }
}