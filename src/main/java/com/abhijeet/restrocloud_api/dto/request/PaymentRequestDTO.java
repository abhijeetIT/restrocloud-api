package com.abhijeet.restrocloud_api.dto.request;

import com.abhijeet.restrocloud_api.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDTO {

    @Builder.Default
    private Double amount= 0.0;

    @NotNull(message = "Payment Method is required")
    private PaymentMethod paymentMethod;

}