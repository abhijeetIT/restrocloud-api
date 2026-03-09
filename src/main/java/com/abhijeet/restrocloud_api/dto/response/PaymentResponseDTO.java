package com.abhijeet.restrocloud_api.dto.response;

import com.abhijeet.restrocloud_api.enums.PaymentMethod;
import com.abhijeet.restrocloud_api.enums.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDTO {

    private Long paymentId;

    private Long orderId;

    private Double amount;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private LocalDateTime paymentTime;

}