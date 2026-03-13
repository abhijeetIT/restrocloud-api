package com.abhijeet.restrocloud_api.service;

import com.abhijeet.restrocloud_api.dto.request.OrderItemRequestDTO;
import com.abhijeet.restrocloud_api.dto.request.OrderRequestDTO;
import com.abhijeet.restrocloud_api.dto.response.OrderResponseDTO;
import com.abhijeet.restrocloud_api.dto.response.PageResponseDTO;
import com.abhijeet.restrocloud_api.enums.OrderStatus;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);

    OrderResponseDTO addOrderItem(Long orderId, OrderItemRequestDTO orderItemRequestDTO);

    OrderResponseDTO updateOrderItem(Long orderId, Long orderItemId, OrderItemRequestDTO orderItemRequestDTO);

    OrderResponseDTO removeOrderItem(Long orderId, Long orderItemId);

    OrderResponseDTO completeOrder(Long orderId);

    OrderResponseDTO getOrderById(Long orderId);

    void cancelOrder(Long orderId);

    PageResponseDTO<OrderResponseDTO> getOrders(
            int page,
            int size,
            OrderStatus status,
            LocalDate startDate,
            LocalDate endDate
    );
}