package com.abhijeet.restrocloud_api.repository;

import com.abhijeet.restrocloud_api.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    Optional<Restaurant> findByEmail(String email);
//    boolean existsByEmail(String email);
   boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);
}
