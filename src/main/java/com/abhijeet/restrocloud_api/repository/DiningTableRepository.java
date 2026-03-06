package com.abhijeet.restrocloud_api.repository;

import com.abhijeet.restrocloud_api.entity.DiningTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiningTableRepository extends JpaRepository<DiningTable,Long> {
    List<DiningTable> findByRestaurantId(Long restaurantId);

    boolean existsByRestaurantIdAndTableNumber(Long restaurantId, Integer tableNumber);

    Optional<DiningTable> findByRestaurantIdAndTableNumber(Long restaurantId, Integer tableNumber);

    Optional<DiningTable> findByIdAndRestaurantId(Long tableId, Long restaurantId); //for deletion
}
