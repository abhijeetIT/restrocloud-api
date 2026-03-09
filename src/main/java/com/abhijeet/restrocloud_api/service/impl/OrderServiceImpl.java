package com.abhijeet.restrocloud_api.service.impl;

import com.abhijeet.restrocloud_api.dto.request.OrderItemRequestDTO;
import com.abhijeet.restrocloud_api.dto.request.OrderRequestDTO;
import com.abhijeet.restrocloud_api.dto.response.OrderResponseDTO;
import com.abhijeet.restrocloud_api.entity.DiningTable;
import com.abhijeet.restrocloud_api.entity.MenuItem;
import com.abhijeet.restrocloud_api.entity.Order;
import com.abhijeet.restrocloud_api.entity.OrderItem;
import com.abhijeet.restrocloud_api.enums.OrderStatus;
import com.abhijeet.restrocloud_api.enums.TableStatus;
import com.abhijeet.restrocloud_api.exception.ResourceNotFoundException;
import com.abhijeet.restrocloud_api.mapper.OrderItemMapper;
import com.abhijeet.restrocloud_api.mapper.OrderMapper;
import com.abhijeet.restrocloud_api.repository.*;
import com.abhijeet.restrocloud_api.service.OrderService;
import com.abhijeet.restrocloud_api.util.RestaurantUtil;
import jakarta.transaction.Transactional;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RestaurantUtil restaurantUtil;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DiningTableRepository diningTableRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    private Double getOrderTotal(Order order) {
        return order.getOrderItems()
                .stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {

        Long restaurantId = restaurantUtil.getLoggedInRestaurantId();

        DiningTable table = diningTableRepository
                .findByIdAndRestaurantId(orderRequestDTO.getTableId(), restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found"));

        if (table.getStatus() == TableStatus.OCCUPIED) {
            throw new IllegalStateException("Table is already occupied");
        }

        Order order = Order.builder()
                .restaurant(table.getRestaurant())
                .table(table)
                .totalAmount(0.0)
                .build();

        if(order.getOrderItems() == null){
            order.setOrderItems(new ArrayList<>());
        }

        orderRepository.save(order);

        table.setStatus(TableStatus.OCCUPIED);

        return orderMapper.mapOrderResponseDTO(order);
    }

    @Override
    public OrderResponseDTO addOrderItem(Long orderId, OrderItemRequestDTO requestDTO) {

        Long loggedRestaurantId = restaurantUtil.getLoggedInRestaurantId();

        // Find Order
        Order order = orderRepository.findByIdAndRestaurantId(orderId, loggedRestaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        // Find MenuItem
        MenuItem menuItem = menuItemRepository
                .findByIdAndRestaurantId(requestDTO.getMenuItemId(), loggedRestaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

        // Check if item already exists in order
        Optional<OrderItem> existingItem = order.getOrderItems()
                .stream()
                .filter(item -> item.getMenuItem().getId().equals(menuItem.getId()))
                .findFirst();

        OrderItem orderItem;
        if (existingItem.isPresent()) {

            orderItem = existingItem.get();
            orderItem.setQuantity(orderItem.getQuantity() + requestDTO.getQuantity());

        } else {

            orderItem = OrderItem.builder()
                    .quantity(requestDTO.getQuantity())
                    .price(menuItem.getPrice())
                    .menuItem(menuItem)
                    .order(order)
                    .build();

            order.getOrderItems().add(orderItem);
        }
        orderItemRepository.save(orderItem);

        Order savedOrder = orderRepository.save(order);

        return orderMapper.mapOrderResponseDTO(savedOrder);
    }

    @Override
    public OrderResponseDTO updateOrderItem(Long orderId, Long orderItemId, OrderItemRequestDTO requestDTO) {

        Long loggedRestaurantId = restaurantUtil.getLoggedInRestaurantId();

        // Find Order
        Order order = orderRepository.findByIdAndRestaurantId(orderId, loggedRestaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        // Find OrderItem
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found"));

        if (requestDTO.getQuantity() <= 0) {

            order.getOrderItems().remove(orderItem);

            orderItemRepository.delete(orderItem);

        } else {

            orderItem.setQuantity(requestDTO.getQuantity());

            orderItemRepository.save(orderItem);
        }

        Order savedOrder = orderRepository.save(order);

        return orderMapper.mapOrderResponseDTO(savedOrder);
    }

    @Override
    public OrderResponseDTO removeOrderItem(Long orderId, Long orderItemId) {

        Long loggedRestaurantId = restaurantUtil.getLoggedInRestaurantId();

        // Find Order
        Order order = orderRepository.findByIdAndRestaurantId(orderId, loggedRestaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        // Find OrderItem
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found"));

        order.getOrderItems().remove(orderItem);

        orderItemRepository.delete(orderItem);

        Order savedOrder = orderRepository.save(order);

        return orderMapper.mapOrderResponseDTO(savedOrder);
    }

    @Override
    public OrderResponseDTO completeOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if(order.getOrderItems().isEmpty()){
            throw new IllegalArgumentException("No item selected till, you only cancle order Not delete allowed");
        }

        order.setTotalAmount(getOrderTotal(order));

        order.setOrderStatus(OrderStatus.COMPLETED);

        Order savedOrder = orderRepository.save(order);

        DiningTable diningTable = diningTableRepository.findByIdAndRestaurantId(order.getTable().getId(), restaurantUtil.getLoggedInRestaurantId())
                .orElseThrow(()->new ResourceNotFoundException("Table Not found in completeOrder Method call. Table id "+order.getTable().getId()));

        diningTable.setStatus(TableStatus.AVAILABLE);

        diningTableRepository.save(diningTable); //set table again to available

        return orderMapper.mapOrderResponseDTO(savedOrder);
    }

    @Override
    public OrderResponseDTO getOrderById(Long orderId) {
        return orderMapper.mapOrderResponseDTO(
                orderRepository.findByIdAndRestaurantId(orderId, restaurantUtil.getLoggedInRestaurantId())
                        .orElseThrow(()->new ResourceNotFoundException("Order not found by id "+orderId))
        );
    }

    @Override
    public void cancelOrder(Long orderId) {

        Order order = orderRepository
                .findByIdAndRestaurantId(orderId, restaurantUtil.getLoggedInRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));

        OrderStatus status = order.getOrderStatus();

        if (status == OrderStatus.CANCELLED
                || status == OrderStatus.SERVED
                || status == OrderStatus.COMPLETED) {

            throw new IllegalArgumentException("Order already " + status + ", cannot cancel");
        }

        order.setOrderStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);
    }
}
