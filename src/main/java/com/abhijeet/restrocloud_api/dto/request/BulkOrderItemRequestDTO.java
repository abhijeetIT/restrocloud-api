package com.abhijeet.restrocloud_api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BulkOrderItemRequestDTO {

    @NotEmpty(message = "At least one order item is required")
    @Valid
    private List<OrderItemRequestDTO> items;

    private String specialInstructions; // Optional instructions for the entire order
}