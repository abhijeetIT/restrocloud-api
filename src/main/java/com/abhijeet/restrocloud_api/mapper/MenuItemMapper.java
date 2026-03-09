package com.abhijeet.restrocloud_api.mapper;

import com.abhijeet.restrocloud_api.dto.response.MenuItemResponseDTO;
import com.abhijeet.restrocloud_api.entity.MenuItem;
import org.springframework.stereotype.Component;

@Component
public class MenuItemMapper {

    public MenuItemResponseDTO mapToMenuItemResponseDTO(MenuItem menuItem) {
        return MenuItemResponseDTO.builder()
                .id(menuItem.getId())
                .name(menuItem.getName())
                .isAvailable(menuItem.getIsAvailable())
                .imageUrl(menuItem.getImageUrl())
                .description(menuItem.getDescription())
                .price(menuItem.getPrice())
                .category(menuItem.getCategory())
                .build();
    }
}
