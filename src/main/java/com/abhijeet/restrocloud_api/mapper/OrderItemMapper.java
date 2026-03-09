package com.abhijeet.restrocloud_api.mapper;

import com.abhijeet.restrocloud_api.dto.response.OrderItemResponseDTO;
import com.abhijeet.restrocloud_api.entity.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    public OrderItemResponseDTO mapOrderItemResponseDTO(OrderItem item){

        double subTotal = item.getPrice() * item.getQuantity();

        return OrderItemResponseDTO.builder()
                .id(item.getId())
                .menuItemId(item.getMenuItem().getId())
                .menuItemName(item.getMenuItem().getName())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .subTotal(subTotal)
                .build();
    }
}
