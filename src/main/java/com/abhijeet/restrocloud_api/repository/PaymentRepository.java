package com.abhijeet.restrocloud_api.repository;

import com.abhijeet.restrocloud_api.entity.Payment;
import com.abhijeet.restrocloud_api.enums.PaymentMethod;
import com.abhijeet.restrocloud_api.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Find by Order (basic)
    Optional<Payment> findByOrderId(Long orderId);

    // Find by Order and Restaurant (with security)
    @Query("SELECT p FROM Payment p WHERE p.order.id = :orderId AND p.order.restaurant.id = :restaurantId")
    Optional<Payment> findByOrderIdAndRestaurantId(@Param("orderId") Long orderId, @Param("restaurantId") Long restaurantId);

    // Find by ID and Restaurant (for security)
    @Query("SELECT p FROM Payment p WHERE p.id = :paymentId AND p.order.restaurant.id = :restaurantId")
    Optional<Payment> findByIdAndRestaurantId(@Param("paymentId") Long paymentId, @Param("restaurantId") Long restaurantId);

    // Find all payments for a restaurant
    @Query("SELECT p FROM Payment p WHERE p.order.restaurant.id = :restaurantId")
    List<Payment> findByRestaurantId(@Param("restaurantId") Long restaurantId);

    // Find by Payment Status for a restaurant
    @Query("SELECT p FROM Payment p WHERE p.order.restaurant.id = :restaurantId AND p.paymentStatus = :status")
    List<Payment> findByRestaurantIdAndPaymentStatus(@Param("restaurantId") Long restaurantId, @Param("status") PaymentStatus status);

    // Find by Payment Method for a restaurant
    @Query("SELECT p FROM Payment p WHERE p.order.restaurant.id = :restaurantId AND p.paymentMethod = :method")
    List<Payment> findByRestaurantIdAndPaymentMethod(@Param("restaurantId") Long restaurantId, @Param("method") PaymentMethod method);

    // Find by date range for a restaurant
    @Query("SELECT p FROM Payment p WHERE p.order.restaurant.id = :restaurantId AND p.paymentTime BETWEEN :startDate AND :endDate")
    List<Payment> findByRestaurantIdAndPaymentTimeBetween(
            @Param("restaurantId") Long restaurantId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    // Get total revenue by date range for a restaurant
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.order.restaurant.id = :restaurantId AND p.paymentStatus = 'COMPLETED' AND p.paymentTime BETWEEN :startDate AND :endDate")
    Double getTotalRevenueByRestaurantIdAndDateRange(
            @Param("restaurantId") Long restaurantId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    // Get total revenue by payment method for a restaurant
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.order.restaurant.id = :restaurantId AND p.paymentStatus = 'COMPLETED' AND p.paymentMethod = :method")
    Double getTotalRevenueByRestaurantIdAndPaymentMethod(
            @Param("restaurantId") Long restaurantId,
            @Param("method") PaymentMethod method);


    // Check if order has payment
    boolean existsByOrderId(Long orderId);


    // Delete payment by order id (with security check in service)
    void deleteByOrderId(Long orderId);

    @Query("""
    SELECT p FROM Payment p
    WHERE 
    p.order.restaurant.id = :restaurantId
    AND (:paymentStatus IS NULL OR p.paymentStatus = :paymentStatus)
    AND (:startDate IS NULL OR DATE(p.paymentTime) >= :startDate)
    AND (:endDate IS NULL OR DATE(p.paymentTime) <= :endDate)
    """)
    Page<Payment> findPaymentsWithFilters(
            @Param("restaurantId") Long restaurantId,
            @Param("paymentStatus") PaymentStatus paymentStatus,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable
    );

    @Query("""
       SELECT COALESCE(SUM(p.amount),0) FROM Payment p
       WHERE p.order.restaurant.id = :restaurantId
       AND p.paymentStatus = 'PAID'
       AND DATE(p.paymentTime) = CURRENT_DATE
       """)
    Double revenueToday(Long restaurantId);

    @Query("""
       SELECT COALESCE(SUM(p.amount),0) FROM Payment p
       WHERE p.order.restaurant.id = :restaurantId
       AND p.paymentStatus = 'PAID'
       AND FUNCTION('MONTH', p.paymentTime) = FUNCTION('MONTH', CURRENT_DATE)
       AND FUNCTION('YEAR', p.paymentTime) = FUNCTION('YEAR', CURRENT_DATE)
       """)
    Double revenueThisMonth(Long restaurantId);


}