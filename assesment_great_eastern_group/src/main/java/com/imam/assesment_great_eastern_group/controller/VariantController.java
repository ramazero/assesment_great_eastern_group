package com.imam.assesment_great_eastern_group.controller;

import com.imam.assesment_great_eastern_group.dto.CreateVariantRequest;
import com.imam.assesment_great_eastern_group.dto.VariantResponse;
import com.imam.assesment_great_eastern_group.entity.Variant;
import com.imam.assesment_great_eastern_group.service.VariantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/variants")
@RequiredArgsConstructor
public class VariantController {

    private final VariantService variantService;

    @PostMapping
    public Variant createVariant(@RequestBody @Valid CreateVariantRequest request) {
        return variantService.createVariant(
                request.getItemId(),
                request.getName(),
                request.getPrice(),
                request.getStock()
        );
    }

    @GetMapping("/item/{itemId}")
    public List<VariantResponse> getByItem(@PathVariable Long itemId) {
        return variantService.getByItem(itemId);
    }
}
