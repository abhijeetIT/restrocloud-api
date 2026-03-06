package com.abhijeet.restrocloud_api.service.impl;

import com.abhijeet.restrocloud_api.dto.request.RestaurantRequestDTO;
import com.abhijeet.restrocloud_api.dto.response.RestaurantResponseDTO;
import com.abhijeet.restrocloud_api.entity.Restaurant;
import com.abhijeet.restrocloud_api.exception.DuplicateResourceException;
import com.abhijeet.restrocloud_api.exception.ResourceNotFoundException;
import com.abhijeet.restrocloud_api.repository.RestaurantRepository;
import com.abhijeet.restrocloud_api.service.RestaurantService;
import com.abhijeet.restrocloud_api.util.RestaurantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RestaurantUtil restaurantUtil;

    private RestaurantResponseDTO mapToResponse(Restaurant restaurant) {
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

    @Override
    public RestaurantResponseDTO signUp(RestaurantRequestDTO dto) {

        if(restaurantRepo.existsByEmailOrPhoneNumber(dto.getEmail(), dto.getPhoneNumber())){
              throw new DuplicateResourceException("Email/PhoneNumber already registered");
        }

        Restaurant restaurant = Restaurant.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))  //encode the raw password
                .address(dto.getAddress())
                .phoneNumber(dto.getPhoneNumber())
                .build();

        Restaurant savedRestaurant = restaurantRepo.save(restaurant);

        return mapToResponse(savedRestaurant);
    }

    @Override
    public RestaurantResponseDTO getRestaurantDetailById(Long id) {
        return mapToResponse(restaurantRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Not found with id "+id)));
    }

    @Override
    public RestaurantResponseDTO getRestaurantDetailByLoggedUser() {
        Long id = restaurantUtil.getLoggedInRestaurantId();

        return mapToResponse(restaurantRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Not found with id "+id)));
    }

    @Override
    public Restaurant getRestaurant(Long id) {
        return restaurantRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("No Restaurent exits by id "+id));
    }

    @Override
    public void deleteRestaurant() {
        Long id = restaurantUtil.getLoggedInRestaurantId();

        Restaurant restaurant = restaurantRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id "+id));

        restaurantRepo.delete(restaurant);
    }


}
