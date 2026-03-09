package com.abhijeet.restrocloud_api.controller;

import com.abhijeet.restrocloud_api.dto.ApiResponse;
import com.abhijeet.restrocloud_api.dto.request.OrderItemRequestDTO;
import com.abhijeet.restrocloud_api.dto.request.OrderRequestDTO;
import com.abhijeet.restrocloud_api.dto.request.PaymentRequestDTO;
import com.abhijeet.restrocloud_api.dto.request.PaymentStatusRequestDTO;
import com.abhijeet.restrocloud_api.service.OrderService;
import com.abhijeet.restrocloud_api.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderApiController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO){
             return ResponseEntity.ok(ApiResponse.builder()
                     .success(true)
                     .message("Order created")
                     .data(orderService.createOrder(orderRequestDTO))
                     .build()
             );
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<?>> getOrderById(@PathVariable Long orderId){
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Order Fetched")
                .success(true)
                .data(orderService.getOrderById(orderId))
                .build()
        );
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<ApiResponse<?>> addOrderItem(@PathVariable Long orderId,@Valid @RequestBody OrderItemRequestDTO orderItemRequestDTO){
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Item added in order")
                .data(orderService.addOrderItem(orderId,orderItemRequestDTO))
                .build()
        );
    }

    @PutMapping("/{orderId}/items/{orderItemId}")
    ResponseEntity<ApiResponse<?>> updateOrderItem(@PathVariable Long orderId, @PathVariable Long orderItemId,
                                                  @Valid @RequestBody OrderItemRequestDTO orderItemRequestDTO){
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Item updated in order")
                .data(orderService.updateOrderItem(orderId,orderItemId,orderItemRequestDTO))
                .build()
        );
    }

    @DeleteMapping("/{orderId}/items/{orderItemId}")
    public ResponseEntity<ApiResponse<?>> removeOrderItem(
            @PathVariable Long orderId,
            @PathVariable Long orderItemId){

        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Item removed from order")
                .data(orderService.removeOrderItem(orderId, orderItemId))
                .build()

        );
    }

    @PutMapping("/{orderId}/complete")
    public ResponseEntity<ApiResponse<?>> completeOrder(@PathVariable Long orderId){
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Order completed, Refer to payment")
                .data(orderService.completeOrder(orderId))
                .build()

        );
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<?>> cancelOrder(@PathVariable Long orderId){
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Order canceled")
                .data(null)
                .build()
        );
    }

    @PostMapping("/{orderId}/payments")
    public ResponseEntity<ApiResponse<?>> processPayment(
            @PathVariable Long orderId,
            @RequestBody PaymentRequestDTO paymentRequestDTO) {

        return ResponseEntity.ok(ApiResponse.builder()
                .message("Payment Generated")
                .success(true)
                .data(paymentService.processPayment(orderId,paymentRequestDTO))
                .build()
        );
    }

    @GetMapping("/{orderId}/payments")
    public ResponseEntity<ApiResponse<?>> getPaymentDetailsByOrderId(@PathVariable Long orderId){
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Payment fetched by order id")
                .success(true)
                .data(paymentService.getPaymentByOrderId(orderId))
                .build()
        );
    }
}
