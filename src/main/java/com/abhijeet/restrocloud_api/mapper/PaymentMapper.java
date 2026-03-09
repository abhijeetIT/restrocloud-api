package com.abhijeet.restrocloud_api.mapper;

import com.abhijeet.restrocloud_api.dto.response.PaymentResponseDTO;
import com.abhijeet.restrocloud_api.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentResponseDTO mapToResponseDTO(Payment payment) {

        return PaymentResponseDTO.builder()
                .paymentId(payment.getId())
                .orderId(payment.getOrder().getId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(payment.getPaymentStatus())
                .paymentTime(payment.getPaymentTime())
                .build();
    }
}