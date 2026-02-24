package com.imam.assesment_great_eastern_group.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SellRequest {

    @NotNull
    @Min(1)
    private Integer quantity;
}
