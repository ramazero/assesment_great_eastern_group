package com.imam.assesment_great_eastern_group.service;

import com.imam.assesment_great_eastern_group.dto.CreateVariantRequest;
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
    public VariantResponse createVariant(CreateVariantRequest request) {

        Variant existing = variantRepository
                .findByItemIdAndName(request.getItemId(), request.getName())
                .orElse(null);

        if (existing != null) {
            // Increase stock instead of creating new row
            existing.setStock(existing.getStock() + request.getStock());

            return new VariantResponse(
                    existing.getId(),
                    existing.getName(),
                    existing.getPrice(),
                    existing.getStock(),
                    existing.getItem().getId(),
                    existing.getItem().getName()
            );
        }

        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        Variant variant = new Variant();
        variant.setItem(item);
        variant.setName(request.getName());
        variant.setPrice(request.getPrice());
        variant.setStock(request.getStock());

        Variant saved = variantRepository.save(variant);

        return new VariantResponse(
                saved.getId(),
                saved.getName(),
                saved.getPrice(),
                saved.getStock(),
                item.getId(),
                item.getName()
        );
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
