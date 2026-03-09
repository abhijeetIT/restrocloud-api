package com.abhijeet.restrocloud_api.repository;

import com.abhijeet.restrocloud_api.entity.Order;
import com.abhijeet.restrocloud_api.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {

        List<Order> findByRestaurantId(Long restaurantId);

        Optional<Order> findByIdAndRestaurantId(Long orderId, Long restaurantId);

        List<Order> findByRestaurantIdAndOrderStatus(Long restaurantId, OrderStatus status);

        long countByRestaurantId(Long restaurantId);


}
