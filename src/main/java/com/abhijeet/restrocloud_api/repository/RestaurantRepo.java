package com.abhijeet.restrocloud_api.repository;

import com.abhijeet.restrocloud_api.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  RestaurantRepo extends JpaRepository<Restaurant,Long> {

}
