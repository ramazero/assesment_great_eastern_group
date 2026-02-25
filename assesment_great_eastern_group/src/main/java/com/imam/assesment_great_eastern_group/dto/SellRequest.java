package com.imam.assesment_great_eastern_group.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellRequest {

    @NotNull
    private Long variantId;

    @NotNull
    @Min(1)
    private Integer quantity;
}
