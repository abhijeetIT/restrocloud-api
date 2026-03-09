package com.abhijeet.restrocloud_api.service.impl;

import com.abhijeet.restrocloud_api.dto.request.PaymentRequestDTO;
import com.abhijeet.restrocloud_api.dto.response.PaymentResponseDTO;
import com.abhijeet.restrocloud_api.mapper.PaymentMapper;
import com.abhijeet.restrocloud_api.repository.*;
import com.abhijeet.restrocloud_api.service.PaymentService;
import com.abhijeet.restrocloud_api.util.RestaurantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private RestaurantUtil restaurantUtil;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentMapper paymentMapper;


    @Override
    public PaymentResponseDTO processPayment(Long orderId, PaymentRequestDTO paymentRequestDTO) {
        return null;
    }

    @Override
    public PaymentResponseDTO getPaymentById(Long paymentId) {
        return null;
    }

    @Override
    public PaymentResponseDTO updatePaymentStatus(Long paymentId, PaymentRequestDTO paymentRequestDTO) {
        return null;
    }
}
