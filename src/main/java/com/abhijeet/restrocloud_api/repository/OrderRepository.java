package com.abhijeet.restrocloud_api.repository;

import com.abhijeet.restrocloud_api.entity.Order;
import com.abhijeet.restrocloud_api.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
                @Param("restaurantId") Long restaurantId,
                @Param("status") OrderStatus status,
                @Param("startDate") LocalDate startDate,
                @Param("endDate") LocalDate endDate,
                Pageable pageable
        );

        @Query("""
       SELECT COUNT(o) FROM Order o
       WHERE o.restaurant.id = :restaurantId
       AND DATE(o.createdAt) = CURRENT_DATE
       """)
        Long countOrdersToday(@Param("restaurantId") Long restaurantId);

        @Query("""
       SELECT COUNT(o) FROM Order o
       WHERE o.restaurant.id = :restaurantId
       AND FUNCTION('MONTH', o.createdAt) = FUNCTION('MONTH', CURRENT_DATE)
       AND FUNCTION('YEAR', o.createdAt) = FUNCTION('YEAR', CURRENT_DATE)
       """)
        Long totalOrdersThisMonth(@Param("restaurantId") Long restaurantId);

        @Query("""
       SELECT COUNT(o) FROM Order o
       WHERE o.restaurant.id = :restaurantId
       AND o.orderStatus = 'CANCELLED'
       AND DATE(o.createdAt) = CURRENT_DATE
       """)
        Long cancelledOrdersToday(@Param("restaurantId") Long restaurantId);

        @Query("""
       SELECT oi.menuItem.name
       FROM OrderItem oi
       WHERE oi.order.restaurant.id = :restaurantId
       GROUP BY oi.menuItem.name
       ORDER BY SUM(oi.quantity) DESC
       LIMIT 1
       """)
        String mostOrderedItem(@Param("restaurantId") Long restaurantId);

        @Query(value = """
        SELECT o.table_id
        FROM orders o
        WHERE o.restaurant_id = :restaurantId
        GROUP BY o.table_id
        ORDER BY COUNT(*) DESC
        LIMIT 1
        """, nativeQuery = true)
        Long busiestTable(@Param("restaurantId") Long restaurantId);
}