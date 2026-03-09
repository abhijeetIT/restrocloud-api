package com.abhijeet.restrocloud_api.service;

import com.abhijeet.restrocloud_api.dto.request.RestaurantRequestDTO;
import com.abhijeet.restrocloud_api.dto.response.RestaurantResponseDTO;
import com.abhijeet.restrocloud_api.entity.Restaurant;

public interface RestaurantService {
     RestaurantResponseDTO signUp(RestaurantRequestDTO dto);
     RestaurantResponseDTO getRestaurantDetailById(Long id);
     RestaurantResponseDTO getRestaurantDetailByLoggedUser();
     Restaurant getRestaurant(Long id); //for object use in table adding etc.
     void deleteRestaurant();

     RestaurantResponseDTO updateRestaurant(RestaurantRequestDTO restaurantRequestDTO);
}
