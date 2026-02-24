package com.imam.assesment_great_eastern_group.service;

import com.imam.assesment_great_eastern_group.entity.Variant;
import com.imam.assesment_great_eastern_group.repository.VariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final VariantRepository variantRepository;

    @Transactional
    public void sell(Long variantId, int quantity) {

        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Variant not found"));

        if (variant.getStock() < quantity) {
            throw new RuntimeException("Out of stock");
        }

        variant.setStock(variant.getStock() - quantity);
    }
}