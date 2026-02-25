package com.imam.assesment_great_eastern_group.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariantResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;

    private Long itemId;
    private String itemName;
}