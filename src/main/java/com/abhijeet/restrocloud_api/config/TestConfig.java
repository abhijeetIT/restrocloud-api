package com.abhijeet.restrocloud_api.config;

import com.abhijeet.restrocloud_api.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Autowired
    private RestaurantService restaurantService;

    @Bean
    public CommandLineRunner commandLineRunner(){
           return new CommandLineRunner() {
               @Override
               public void run(String... args) throws Exception {
//                   RestaurantRequestDTO rt = new RestaurantRequestDTO("Khai Khai Restaurant","khairestaurant@gmail.com","KhaiKhai@123","Boropada,Hooghly","9503601082");
//                   if(restaurantService.Register(rt)){
//                       System.out.println("Inserted");
//                   }else{
//                       System.out.println("SOmething wrong");
//                   }
               }
           };
    }
}
