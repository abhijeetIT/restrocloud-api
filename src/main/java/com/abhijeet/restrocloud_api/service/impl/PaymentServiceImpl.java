package com.abhijeet.restrocloud_api.service.impl;

import com.abhijeet.restrocloud_api.dto.request.PaymentRequestDTO;
import com.abhijeet.restrocloud_api.dto.request.PaymentStatusRequestDTO;
import com.abhijeet.restrocloud_api.dto.response.PageResponseDTO;
import com.abhijeet.restrocloud_api.dto.response.PaymentResponseDTO;
import com.abhijeet.restrocloud_api.entity.Order;
import com.abhijeet.restrocloud_api.entity.Payment;
import com.abhijeet.restrocloud_api.enums.OrderStatus;
import com.abhijeet.restrocloud_api.enums.PaymentStatus;
import com.abhijeet.restrocloud_api.exception.BadRequestException;
import com.abhijeet.restrocloud_api.exception.ResourceNotFoundException;
import com.abhijeet.restrocloud_api.mapper.PaymentMapper;
import com.abhijeet.restrocloud_api.repository.*;
import com.abhijeet.restrocloud_api.service.PaymentService;
import com.abhijeet.restrocloud_api.util.RestaurantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    @Transactional
    public PaymentResponseDTO processPayment(Long orderId, PaymentRequestDTO paymentRequestDTO) {
        Long loggedInRestaurantId = restaurantUtil.getLoggedInRestaurantId();

        Order order = orderRepository.findByIdAndRestaurantId(orderId, loggedInRestaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("The order not found to proceed payment"));

        OrderStatus status = order.getOrderStatus();

        // FIXED: Only allow payment for SERVED or COMPLETED orders
        if (status != OrderStatus.SERVED && status != OrderStatus.COMPLETED) {
            throw new BadRequestException(
                    String.format("Order is %s. Payment can only be processed for SERVED or COMPLETED orders", status)
            );
        }

        // Check if payment already exists
        if (paymentRepository.existsByOrderId(orderId)) {
            throw new BadRequestException("Payment already exists for this order");
        }

        Payment payment = Payment.builder()
                .amount(order.getTotalAmount())
                .paymentStatus(PaymentStatus.PENDING)
                .paymentMethod(paymentRequestDTO.getPaymentMethod())
                .order(order)
                .build();

        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.mapToResponseDTO(savedPayment);
    }

    @Override
    public PaymentResponseDTO getPaymentById(Long paymentId) {
        Long loggedInRestaurantId = restaurantUtil.getLoggedInRestaurantId();

        return paymentMapper.mapToResponseDTO(paymentRepository.findByIdAndRestaurantId(paymentId, loggedInRestaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment details not found in your restaurant with id " + paymentId))
        );
    }

    @Override
    public PaymentResponseDTO getPaymentByOrderId(Long orderId) {
        Long loggedInRestaurantId = restaurantUtil.getLoggedInRestaurantId();

        return paymentMapper.mapToResponseDTO(paymentRepository.findByOrderIdAndRestaurantId(orderId, loggedInRestaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment details not found in your restaurant with Order id " + orderId))
        );
    }

    @Override
    @Transactional
    public PaymentResponseDTO updatePaymentStatus(Long paymentId, PaymentStatusRequestDTO paymentStatusRequestDTO) {
        Long loggedInRestaurantId = restaurantUtil.getLoggedInRestaurantId();

        Payment payment = paymentRepository.findByIdAndRestaurantId(paymentId, loggedInRestaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment details not found in your restaurant with id " + paymentId));

        PaymentStatus currentStatus = payment.getPaymentStatus();
        PaymentStatus newStatus = paymentStatusRequestDTO.getPaymentStatus();

        // Validate transitions based on enums
        if (currentStatus == PaymentStatus.PAID) {
            throw new BadRequestException("Payment is already PAID, cannot update status");
        }

        if (currentStatus == PaymentStatus.FAILED && newStatus != PaymentStatus.PENDING) {
            throw new BadRequestException("Failed payment can only be retried to PENDING");
        }

        if (currentStatus == PaymentStatus.PENDING && newStatus == PaymentStatus.PENDING) {
            throw new BadRequestException("Payment already in PENDING state");
        }

        payment.setPaymentStatus(newStatus);

        // If payment is PAID, update payment time and order status
        if (newStatus == PaymentStatus.PAID) {
            payment.setPaymentTime(LocalDateTime.now());

            Order order = payment.getOrder();
            order.setOrderStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);
        }

        return paymentMapper.mapToResponseDTO(paymentRepository.save(payment));
    }

    @Override
    @Transactional
    public PaymentResponseDTO updatePaymentMethod(Long paymentId, PaymentRequestDTO paymentRequestDTO) {
        Long loggedInRestaurantId = restaurantUtil.getLoggedInRestaurantId();

        Payment payment = paymentRepository.findByIdAndRestaurantId(paymentId, loggedInRestaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment details not found in your restaurant with id " + paymentId));

        if (payment.getPaymentStatus() == PaymentStatus.PAID) {
            throw new BadRequestException("Payment is already PAID, cannot update payment method");
        }

        payment.setPaymentMethod(paymentRequestDTO.getPaymentMethod());

        return paymentMapper.mapToResponseDTO(paymentRepository.save(payment));
    }

    @Override
    public List<PaymentResponseDTO> getAllPayment() {
        Long loggedInRestaurantId = restaurantUtil.getLoggedInRestaurantId();

        List<Payment> payments = paymentRepository.findByRestaurantId(loggedInRestaurantId);

        return payments.stream()
                .map(paymentMapper::mapToResponseDTO)
                .toList()
                ;
    }

    @Override
    public PageResponseDTO<PaymentResponseDTO> getPaymentsByFilter(int currentPage, int size, PaymentStatus status, LocalDate startDate, LocalDate endDate) {
        Long loggedInRestaurantId = restaurantUtil.getLoggedInRestaurantId();

        Pageable pageable = PageRequest.of(currentPage,size, Sort.by("paymentTime").descending());

        try{
            Page<Payment> paymentsPage = paymentRepository.findPaymentsWithFilters(loggedInRestaurantId,status,startDate,endDate,pageable);

            List<PaymentResponseDTO> payments = paymentsPage.getContent()
                    .stream()
                    .map(paymentMapper::mapToResponseDTO)
                    .toList();

            return PageResponseDTO.<PaymentResponseDTO>builder()
                    .content(payments)
                    .page(paymentsPage.getNumber())
                    .size(paymentsPage.getSize())
                    .totalElements(paymentsPage.getTotalElements())
                    .totalPages(paymentsPage.getTotalPages())
                    .last(paymentsPage.isLast())
                    .build();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}