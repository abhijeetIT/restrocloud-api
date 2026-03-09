package com.abhijeet.restrocloud_api.controller;

import com.abhijeet.restrocloud_api.dto.ApiResponse;
import com.abhijeet.restrocloud_api.dto.request.PaymentRequestDTO;
import com.abhijeet.restrocloud_api.dto.request.PaymentStatusRequestDTO;
import com.abhijeet.restrocloud_api.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentApiController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<?>> getPaymentDetailsById(@PathVariable Long paymentId){
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Payment fetched by payment id")
                .success(true)
                .data(paymentService.getPaymentById(paymentId))
                .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllPaymentDetails() {
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Payment fetched by payment id")
                .success(true)
                .data(paymentService.getAllPayment())
                .build()
        );
    }

    @PatchMapping("/{paymentId}/status")
    public ResponseEntity<ApiResponse<?>> updatePaymentStatus(
            @PathVariable Long paymentId,
            @Valid @RequestBody PaymentStatusRequestDTO paymentStatusRequestDTO) {

        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Payment status updated")
                .data(paymentService.updatePaymentStatus(paymentId, paymentStatusRequestDTO))
                .build()
        );
    }

    @PatchMapping("/{paymentId}/method")
    public ResponseEntity<ApiResponse<?>> updatePaymentMethod(
            @PathVariable Long paymentId,
            @RequestBody PaymentRequestDTO requestDTO) {

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Payment method updated successfully")
                        .data(paymentService.updatePaymentMethod(paymentId, requestDTO))
                        .build()
        );
    }
}
