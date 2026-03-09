package com.abhijeet.restrocloud_api.service;


import com.abhijeet.restrocloud_api.dto.request.PaymentRequestDTO;
import com.abhijeet.restrocloud_api.dto.response.PaymentResponseDTO;
import com.abhijeet.restrocloud_api.entity.Payment;

public interface PaymentService {

    PaymentResponseDTO processPayment(Long orderId, PaymentRequestDTO paymentRequestDTO);

    PaymentResponseDTO getPaymentById(Long paymentId);

    PaymentResponseDTO updatePaymentStatus(Long paymentId, PaymentRequestDTO paymentRequestDTO);
}
