package com.abhijeet.restrocloud_api.repository;

import com.abhijeet.restrocloud_api.entity.Payment;
import com.abhijeet.restrocloud_api.enums.PaymentMethod;
import com.abhijeet.restrocloud_api.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    // Find successful payments for a restaurant
    @Query("SELECT p FROM Payment p WHERE p.order.restaurant.id = :restaurantId AND p.paymentStatus = 'COMPLETED'")
    List<Payment> findSuccessfulPaymentsByRestaurantId(@Param("restaurantId") Long restaurantId);

    // Find failed payments for a restaurant
    @Query("SELECT p FROM Payment p WHERE p.order.restaurant.id = :restaurantId AND p.paymentStatus = 'FAILED'")
    List<Payment> findFailedPaymentsByRestaurantId(@Param("restaurantId") Long restaurantId);

    // Find pending payments for a restaurant
    @Query("SELECT p FROM Payment p WHERE p.order.restaurant.id = :restaurantId AND p.paymentStatus = 'PENDING'")
    List<Payment> findPendingPaymentsByRestaurantId(@Param("restaurantId") Long restaurantId);

    // Count payments by status for a restaurant
    @Query("SELECT COUNT(p) FROM Payment p WHERE p.order.restaurant.id = :restaurantId AND p.paymentStatus = :status")
    long countByRestaurantIdAndPaymentStatus(@Param("restaurantId") Long restaurantId, @Param("status") PaymentStatus status);

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

    // Get daily revenue for a restaurant (group by date)
    @Query("SELECT DATE(p.paymentTime), SUM(p.amount) FROM Payment p " +
            "WHERE p.order.restaurant.id = :restaurantId AND p.paymentStatus = 'COMPLETED' " +
            "AND p.paymentTime BETWEEN :startDate AND :endDate " +
            "GROUP BY DATE(p.paymentTime) ORDER BY DATE(p.paymentTime)")
    List<Object[]> getDailyRevenueByRestaurantIdAndDateRange(
            @Param("restaurantId") Long restaurantId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    // Check if order has payment
    boolean existsByOrderId(Long orderId);

    // Check if order has successful payment
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Payment p WHERE p.order.id = :orderId AND p.paymentStatus = 'COMPLETED'")
    boolean hasSuccessfulPaymentByOrderId(@Param("orderId") Long orderId);

    // Find payments by table number (through order)
    @Query("SELECT p FROM Payment p WHERE p.order.table.id = :tableId AND p.order.restaurant.id = :restaurantId")
    List<Payment> findByTableIdAndRestaurantId(@Param("tableId") Long tableId, @Param("restaurantId") Long restaurantId);

    // Get payment summary statistics for a restaurant
    @Query("SELECT " +
            "COUNT(p), " +
            "SUM(CASE WHEN p.paymentStatus = 'COMPLETED' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN p.paymentStatus = 'PENDING' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN p.paymentStatus = 'FAILED' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN p.paymentStatus = 'COMPLETED' THEN p.amount ELSE 0 END) " +
            "FROM Payment p WHERE p.order.restaurant.id = :restaurantId")
    Object[] getPaymentStatisticsByRestaurantId(@Param("restaurantId") Long restaurantId);

    // Get payment method breakdown for a restaurant
    @Query("SELECT p.paymentMethod, COUNT(p), SUM(p.amount) FROM Payment p " +
            "WHERE p.order.restaurant.id = :restaurantId AND p.paymentStatus = 'COMPLETED' " +
            "GROUP BY p.paymentMethod")
    List<Object[]> getPaymentMethodBreakdownByRestaurantId(@Param("restaurantId") Long restaurantId);

    // Delete payment by order id (with security check in service)
    void deleteByOrderId(Long orderId);
}