package com.imam.assesment_great_eastern_group.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SaleResponse {
    private Long saleId;
    private Long variantId;
    private String variantName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalAmount;
    private LocalDateTime soldAt;
}
