package com.imam.assesment_great_eastern_group.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateItemRequest {

    @NotBlank(message = "Item name is required")
    private String name;
}
