package com.abhijeet.restrocloud_api.service.impl;


import com.abhijeet.restrocloud_api.dto.response.OrderItemResponseDTO;
import com.abhijeet.restrocloud_api.entity.OrderItem;
import com.abhijeet.restrocloud_api.repository.DiningTableRepository;
import com.abhijeet.restrocloud_api.repository.MenuItemRepository;
import com.abhijeet.restrocloud_api.repository.OrderRepository;
import com.abhijeet.restrocloud_api.repository.RestaurantRepository;
import com.abhijeet.restrocloud_api.util.RestaurantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl {

    @Autowired
    private RestaurantUtil restaurantUtil;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DiningTableRepository diningTableRepository;

    @Autowired
    private OrderRepository orderRepository;



}
