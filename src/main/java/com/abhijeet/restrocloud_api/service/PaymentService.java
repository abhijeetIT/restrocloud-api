package com.abhijeet.restrocloud_api.service;

import com.abhijeet.restrocloud_api.dto.request.PaymentRequestDTO;
import com.abhijeet.restrocloud_api.dto.request.PaymentStatusRequestDTO;
import com.abhijeet.restrocloud_api.dto.response.PageResponseDTO;
import com.abhijeet.restrocloud_api.dto.response.PaymentResponseDTO;
import com.abhijeet.restrocloud_api.enums.PaymentStatus;

import java.time.LocalDate;
import java.util.List;

public interface PaymentService {

    PaymentResponseDTO processPayment(Long orderId, PaymentRequestDTO paymentRequestDTO);

    PaymentResponseDTO getPaymentById(Long paymentId);

    PaymentResponseDTO getPaymentByOrderId(Long orderId);

    PaymentResponseDTO updatePaymentStatus(Long paymentId, PaymentStatusRequestDTO paymentStatusRequestDTO);

    PaymentResponseDTO updatePaymentMethod(Long paymentId, PaymentRequestDTO paymentRequestDTO);

    List<PaymentResponseDTO> getAllPayment();

    PageResponseDTO<PaymentResponseDTO> getPaymentsByFilter(int currentPage, int size, PaymentStatus status, LocalDate startDate, LocalDate endDate);
}
