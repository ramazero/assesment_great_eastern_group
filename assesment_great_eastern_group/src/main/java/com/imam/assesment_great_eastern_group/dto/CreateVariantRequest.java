package com.imam.assesment_great_eastern_group.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVariantRequest {

    @NotNull
    private Long itemId;

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @Min(0)
    private Integer stock;
}