package com.abhijeet.restrocloud_api.dto.response;

import com.abhijeet.restrocloud_api.enums.Category;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemResponseDTO {

    private Long id;
    private String name;
    private Double price;
    private String description;
    private String imageUrl;
    private Boolean isAvailable;
    private Category category;
}