package com.imam.assesment_great_eastern_group.controller;

import com.imam.assesment_great_eastern_group.dto.SaleResponse;
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

    @PostMapping
    public SaleResponse createSale(
            @RequestBody @Valid SellRequest request
    ) {
        return saleService.createSale(request);
    }
}