package com.abhijeet.restrocloud_api.util;

import com.abhijeet.restrocloud_api.entity.Restaurant;
import com.abhijeet.restrocloud_api.exception.ResourceNotFoundException;
import com.abhijeet.restrocloud_api.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RestaurantUtil {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Restaurant getLoggedInRestaurantObject() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return restaurantRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Something Wrong Please log in again"));
    }

    public Long getLoggedInRestaurantId(){
        return getLoggedInRestaurantObject().getId();
    }

}
