package com.abhijeet.restrocloud_api.dto.request;


import com.abhijeet.restrocloud_api.enums.PaymentMethod;
import com.abhijeet.restrocloud_api.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentStatusRequestDTO {
    @NotNull(message = "Payment status is required")
    private PaymentStatus paymentStatus;
}
