package com.abhijeet.restrocloud_api.mapper;

import com.abhijeet.restrocloud_api.dto.response.DiningTableResponseDTO;
import com.abhijeet.restrocloud_api.entity.DiningTable;
import org.springframework.stereotype.Component;

@Component
public class DiningTableMapper {

    public DiningTableResponseDTO mapToDiningTableResponseDTO(DiningTable diningTable) {
        return DiningTableResponseDTO.builder()
                .id(diningTable.getId())
                .tableNumber(diningTable.getTableNumber())
                .capacity(diningTable.getCapacity())
                .restaurantId(diningTable.getRestaurant().getId())
                .status(diningTable.getStatus())
                .build();
    }

}
