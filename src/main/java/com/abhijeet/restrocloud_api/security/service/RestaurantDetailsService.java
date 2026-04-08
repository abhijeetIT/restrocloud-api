package com.abhijeet.restrocloud_api.security.service;

import com.abhijeet.restrocloud_api.entity.Restaurant;
import com.abhijeet.restrocloud_api.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RestaurantDetailsService implements UserDetailsService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantDetailsService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Restaurant restaurant = restaurantRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Restaurant not found with email: " + email)
                );

        if (!Boolean.TRUE.equals(restaurant.getIsActive())) {
            throw new UsernameNotFoundException("Restaurant account is inactive/deleted.");
        }

        return User.builder()
                .username(restaurant.getEmail())
                .password(restaurant.getPassword())
                .roles(restaurant.getRole().name()) // if enum
                .build();
    }
}