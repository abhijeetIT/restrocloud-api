package com.abhijeet.restrocloud_api.dto.request;

import com.abhijeet.restrocloud_api.enums.Category;
import jakarta.validation.constraints.*;
        import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemRequestDTO {

    @NotBlank(message = "Item name is required")
    @Size(min = 2, max = 100, message = "Item name must be between 2 and 100 characters")
    private String name;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must have at most 2 decimal places")
    private Double price;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @Builder.Default
    private String imageUrl=null;

    @Builder.Default
    private Boolean isAvailable = true;

    @NotNull(message = "Category is required")
    private Category category;

}