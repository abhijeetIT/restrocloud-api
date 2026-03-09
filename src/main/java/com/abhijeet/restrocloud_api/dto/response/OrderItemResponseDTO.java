package com.abhijeet.restrocloud_api.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDTO {

    private Long id;

    private Long menuItemId;

    private String menuItemName;

    private Integer quantity;

    private Double price;

    private Double subTotal;
}