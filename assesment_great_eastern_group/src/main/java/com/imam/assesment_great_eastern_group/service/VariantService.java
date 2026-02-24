package com.imam.assesment_great_eastern_group.service;

import com.imam.assesment_great_eastern_group.dto.VariantResponse;
import com.imam.assesment_great_eastern_group.entity.Item;
import com.imam.assesment_great_eastern_group.entity.Variant;
import com.imam.assesment_great_eastern_group.repository.ItemRepository;
import com.imam.assesment_great_eastern_group.repository.VariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VariantService {

    private final VariantRepository variantRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Variant createVariant(Long itemId, String name, BigDecimal price, Integer stock) {

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        Variant variant = Variant.builder()
                .name(name)
                .price(price)
                .stock(stock)
                .item(item)
                .build();

        return variantRepository.save(variant);
    }

    @Transactional(readOnly = true)
    public List<VariantResponse> getByItem(Long itemId) {
        List<Variant> variants = variantRepository.findByItemId(itemId);

        return variants.stream()
                .map(v -> new VariantResponse(
                        v.getId(),
                        v.getName(),
                        v.getPrice(),
                        v.getStock(),
                        v.getItem().getId(),
                        v.getItem().getName()
                ))
                .toList();
    }
}
