package com.abhijeet.restrocloud_api.controller;

import com.abhijeet.restrocloud_api.dto.ApiResponse;
import com.abhijeet.restrocloud_api.dto.request.PaymentRequestDTO;
import com.abhijeet.restrocloud_api.dto.response.PaymentResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @PostMapping("/orders/{orderId}/payments")
    public ResponseEntity<ApiResponse<?>> processPayment(
            @PathVariable Long orderId,
            @RequestBody PaymentRequestDTO dto) {

        return ResponseEntity.ok(ApiResponse.builder()
                .message("Payment Generated")
                .success(true)
                .data(null)
                .build()

        );
    }

}
