package com.abhijeet.restrocloud_api.mapper;

import com.abhijeet.restrocloud_api.dto.response.RestaurantResponseDTO;
import com.abhijeet.restrocloud_api.entity.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    public RestaurantResponseDTO mapToRestaurantResponseDTO(Restaurant restaurant) {
        return RestaurantResponseDTO.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .email(restaurant.getEmail())
                .address(restaurant.getAddress())
                .phoneNumber(restaurant.getPhoneNumber())
                .isActive(restaurant.getIsActive())
                .role(restaurant.getRole())
                .createdAt(restaurant.getCreatedAt())
                .build();
    }
}
