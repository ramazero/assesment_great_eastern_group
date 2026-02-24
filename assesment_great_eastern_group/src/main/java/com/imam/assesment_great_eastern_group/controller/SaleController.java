package com.imam.assesment_great_eastern_group.controller;

import com.imam.assesment_great_eastern_group.dto.SellRequest;
import com.imam.assesment_great_eastern_group.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping("/{variantId}")
    public String sell(
            @PathVariable Long variantId,
            @RequestBody @Valid SellRequest request
    ) {
        saleService.sell(variantId, request.getQuantity());
        return "Sale successful";
    }
}
