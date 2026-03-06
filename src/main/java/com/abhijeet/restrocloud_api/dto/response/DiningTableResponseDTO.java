package com.abhijeet.restrocloud_api.dto.response;

import com.abhijeet.restrocloud_api.enums.TableStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiningTableResponseDTO {

    private Long id;

    private Integer tableNumber;

    private Integer capacity;

    private TableStatus status;

    private Long restaurantId; // useful to know which restaurant it belongs to
}