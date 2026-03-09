package com.abhijeet.restrocloud_api.dto.response;

import com.abhijeet.restrocloud_api.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {

    private Long id;

    private Long tableId;

    private Integer tableNumber;

    private Double totalAmount;

    private OrderStatus orderStatus;

    private LocalDateTime createdAt;

    private List<OrderItemResponseDTO> items;
}