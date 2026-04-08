package com.abhijeet.restrocloud_api.controller;


import com.abhijeet.restrocloud_api.dto.ApiResponse;
import com.abhijeet.restrocloud_api.dto.request.RestaurantRequestDTO;
import com.abhijeet.restrocloud_api.dto.request.UpdateRestaurantRequestDTO;
import com.abhijeet.restrocloud_api.repository.RestaurantRepository;
import com.abhijeet.restrocloud_api.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantApiController {

    @Autowired
    private RestaurantService restaurantService;

    //for fetching current restaurant
    @GetMapping
    public ResponseEntity<ApiResponse<?>> resturentDetail(){
             return ResponseEntity.ok(ApiResponse.builder()
                     .success(true)
                     .message("Fetched successfully")
                     .data(restaurantService.getRestaurantDetailByLoggedUser())
                     .build()
             );
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<?>> deleteResturant(){

        restaurantService.deleteRestaurant();

        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Your account are in going to be deleted after 15days . it is for data protection. want id back so contact us")
                .data(null)
                .build()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<?>> updateRestaurantDetails( @Valid @RequestBody UpdateRestaurantRequestDTO updateRestaurantRequestDTO){
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Details Updated successfully")
                .data(restaurantService.updateRestaurant(updateRestaurantRequestDTO))
                .build()
        );
    }
}
