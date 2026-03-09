package com.abhijeet.restrocloud_api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemRequestDTO {

    @NotNull(message = "Menu item id is required")
    private Long menuItemId;

    @NotNull(message = "Quantity required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}