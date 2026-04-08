package com.abhijeet.restrocloud_api.service.impl;

import com.abhijeet.restrocloud_api.dto.response.SummaryResponseDTO;
import com.abhijeet.restrocloud_api.repository.OrderRepository;
import com.abhijeet.restrocloud_api.repository.PaymentRepository;
import com.abhijeet.restrocloud_api.repository.RestaurantRepository;
import com.abhijeet.restrocloud_api.service.AnalyticsService;
import com.abhijeet.restrocloud_api.util.RestaurantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    @Autowired
    private RestaurantUtil restaurantUtil;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public SummaryResponseDTO getSummary() {
        Long loogedInRestaurantId = restaurantUtil.getLoggedInRestaurantId();

        return SummaryResponseDTO.builder()
                .totalOrdersToday(orderRepository.countOrdersToday(loogedInRestaurantId))
                .revenueToday(paymentRepository.revenueToday(loogedInRestaurantId))
                .revenueThisMonth(paymentRepository.revenueThisMonth(loogedInRestaurantId))
                .totalOrdersThisMonth(orderRepository.totalOrdersThisMonth(loogedInRestaurantId))
                .mostOrderedItem(orderRepository.mostOrderedItem(loogedInRestaurantId))
                .busiestTable(orderRepository.busiestTable(loogedInRestaurantId))
                .cancelledOrdersToday(orderRepository.cancelledOrdersToday(loogedInRestaurantId))
                .build();
    }
}
