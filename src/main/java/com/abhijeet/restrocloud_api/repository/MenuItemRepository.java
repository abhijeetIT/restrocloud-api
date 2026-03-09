package com.abhijeet.restrocloud_api.repository;

import com.abhijeet.restrocloud_api.entity.MenuItem;
import com.abhijeet.restrocloud_api.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    // Find all menu items by restaurant ID
    List<MenuItem> findByRestaurantId(Long restaurantId);

    // Find all menu items by restaurant ID and category
    List<MenuItem> findByRestaurantIdAndCategory(Long restaurantId, Category category);

    // Find all available menu items by restaurant ID
    List<MenuItem> findByRestaurantIdAndIsAvailableTrue(Long restaurantId);

    // Check if a menu item exists with the same name in a restaurant
    boolean existsByRestaurantIdAndName(Long restaurantId, String name);

    // Find menu item by restaurant ID and name
    Optional<MenuItem> findByRestaurantIdAndName(Long restaurantId, String name);

    // Find menu item by ID and restaurant ID (for security - ensures item belongs to restaurant)
    Optional<MenuItem> findByIdAndRestaurantId(Long itemId, Long restaurantId);

    // Check if a menu item exists with the same name in a restaurant excluding a specific ID (for updates)
    boolean existsByRestaurantIdAndNameAndIdNot(
            Long restaurantId,
            String name,
            Long id
    );

    // Find menu items by category (across all restaurants)
    List<MenuItem> findByCategory(Category category);

    // Search menu items by name containing keyword (case insensitive)
    @Query("SELECT m FROM MenuItem m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<MenuItem> searchByNameContaining(@Param("keyword") String keyword);

    // Count menu items by restaurant
    Long countByRestaurantId(Long restaurantId);

    // Delete all menu items by restaurant ID
    void deleteByRestaurantId(Long restaurantId);
}