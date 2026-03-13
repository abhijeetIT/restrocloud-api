package com.abhijeet.restrocloud_api.repository;

import com.abhijeet.restrocloud_api.entity.Order;
import com.abhijeet.restrocloud_api.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {

        List<Order> findByRestaurantId(Long restaurantId);

        Optional<Order> findByIdAndRestaurantId(Long orderId, Long restaurantId);

        List<Order> findByRestaurantIdAndOrderStatus(Long restaurantId, OrderStatus status);

        long countByRestaurantId(Long restaurantId);

        @Query("""
             SELECT o FROM Order o
             WHERE o.restaurant.id = :restaurantId
             AND (:status IS NULL OR o.orderStatus = :status)
             AND (:startDate IS NULL OR DATE(o.createdAt) >= :startDate)
             AND (:endDate IS NULL OR DATE(o.createdAt) <= :endDate)
             """)
        Page<Order> findOrdersWithFilters(
                Long restaurantId,
                OrderStatus status,
                LocalDate startDate,
                LocalDate endDate,
                Pageable pageable
        );

}
