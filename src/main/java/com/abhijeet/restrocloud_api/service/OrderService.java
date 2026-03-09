package com.abhijeet.restrocloud_api.service;

import com.abhijeet.restrocloud_api.dto.request.OrderItemRequestDTO;
import com.abhijeet.restrocloud_api.dto.request.OrderRequestDTO;
import com.abhijeet.restrocloud_api.dto.response.OrderResponseDTO;

public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);

    OrderResponseDTO addOrderItem(Long orderId, OrderItemRequestDTO orderItemRequestDTO);

    OrderResponseDTO updateOrderItem(Long orderId, Long orderItemId, OrderItemRequestDTO orderItemRequestDTO);

    OrderResponseDTO removeOrderItem(Long orderId, Long orderItemId);

    OrderResponseDTO completeOrder(Long orderId);

    OrderResponseDTO getOrderById(Long orderId);

    void cancelOrder(Long orderId);
}