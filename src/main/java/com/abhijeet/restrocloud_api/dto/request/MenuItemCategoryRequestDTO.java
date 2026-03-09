package com.abhijeet.restrocloud_api.dto.request;

import com.abhijeet.restrocloud_api.enums.Category;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemCategoryRequestDTO {

    @NotNull(message = "Category is required")
    private Category category;

}