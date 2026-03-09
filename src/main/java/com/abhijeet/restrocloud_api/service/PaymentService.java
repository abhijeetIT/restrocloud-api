package com.abhijeet.restrocloud_api.service;

import com.abhijeet.restrocloud_api.dto.request.PaymentRequestDTO;
import com.abhijeet.restrocloud_api.dto.request.PaymentStatusRequestDTO;
import com.abhijeet.restrocloud_api.dto.response.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {

    PaymentResponseDTO processPayment(Long orderId, PaymentRequestDTO paymentRequestDTO);

    PaymentResponseDTO getPaymentById(Long paymentId);

    PaymentResponseDTO getPaymentByOrderId(Long orderId);

    PaymentResponseDTO updatePaymentStatus(Long paymentId, PaymentStatusRequestDTO paymentStatusRequestDTO);

    PaymentResponseDTO updatePaymentMethod(Long paymentId, PaymentRequestDTO paymentRequestDTO);

    List<PaymentResponseDTO> getAllPayment();
}
