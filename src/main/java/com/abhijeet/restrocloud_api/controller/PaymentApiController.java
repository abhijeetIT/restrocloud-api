package com.abhijeet.restrocloud_api.controller;

import com.abhijeet.restrocloud_api.dto.ApiResponse;
import com.abhijeet.restrocloud_api.dto.request.PaymentRequestDTO;
import com.abhijeet.restrocloud_api.dto.request.PaymentStatusRequestDTO;
import com.abhijeet.restrocloud_api.enums.PaymentStatus;
import com.abhijeet.restrocloud_api.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/payments")
public class PaymentApiController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<?>> getPaymentDetailsById(@PathVariable Long paymentId){
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Payment fetched successfully by payment id")
                .success(true)
                .data(paymentService.getPaymentById(paymentId))
                .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getPaymentsByFilter(
            @RequestParam(defaultValue = "0") int currentPage,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false)PaymentStatus status,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
            ) {
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Payment fetched successfully")
                .success(true)
                .data(paymentService.getPaymentsByFilter(currentPage,size,status,startDate,endDate))
                .build()
        );
    }

    @PatchMapping("/{paymentId}/status")
    public ResponseEntity<ApiResponse<?>> updatePaymentStatus(
            @PathVariable Long paymentId,
            @Valid @RequestBody PaymentStatusRequestDTO paymentStatusRequestDTO) {

        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Payment status updated successfully")
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
