package com.abhijeet.restrocloud_api.dto.response;

import com.abhijeet.restrocloud_api.enums.Role;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantResponseDTO {

    private Long id;

    private String name;

    private String email;

    private String address;

    private String phoneNumber;

    private Boolean isActive;

    private Role role;

    private LocalDateTime createdAt;
}