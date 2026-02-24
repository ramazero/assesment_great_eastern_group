package com.imam.assesment_great_eastern_group.service;

import com.imam.assesment_great_eastern_group.dto.SaleResponse;
import com.imam.assesment_great_eastern_group.dto.SellRequest;
import com.imam.assesment_great_eastern_group.entity.Sale;
import com.imam.assesment_great_eastern_group.entity.Variant;
import com.imam.assesment_great_eastern_group.repository.SaleRepository;
import com.imam.assesment_great_eastern_group.repository.VariantRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final VariantRepository variantRepository;
    private final SaleRepository saleRepository;

    @Transactional
    public SaleResponse createSale(SellRequest request) {

        Variant variant = variantRepository.findById(request.getVariantId())
                .orElseThrow(() -> new RuntimeException("Variant not found"));

        if (variant.getStock() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        variant.setStock(variant.getStock() - request.getQuantity());

        BigDecimal total = variant.getPrice()
                .multiply(BigDecimal.valueOf(request.getQuantity()));

        Sale sale = new Sale();
        sale.setVariant(variant);
        sale.setQuantity(request.getQuantity());
        sale.setUnitPrice(variant.getPrice());
        sale.setTotalAmount(total);

        saleRepository.save(sale);

        return new SaleResponse(
                sale.getId(),
                variant.getId(),
                variant.getName(),
                sale.getQuantity(),
                sale.getUnitPrice(),
                sale.getTotalAmount(),
                sale.getSoldAt()
        );
    }
}