package com.abhijeet.restrocloud_api.mapper;

import com.abhijeet.restrocloud_api.dto.response.OrderItemResponseDTO;
import com.abhijeet.restrocloud_api.dto.response.OrderResponseDTO;
import com.abhijeet.restrocloud_api.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    @Autowired
    private OrderItemMapper orderItemMapper;

    public OrderResponseDTO mapOrderResponseDTO(Order order){

        List<OrderItemResponseDTO> items = order.getOrderItems()
                .stream()
                .map(orderItemMapper::mapOrderItemResponseDTO)
                .toList();

        return OrderResponseDTO.builder()
                .id(order.getId())
                .tableId(order.getTable().getId())
                .tableNumber(order.getTable().getTableNumber())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus())
                .createdAt(order.getCreatedAt())
                .items(items)
                .build();
    }
}
