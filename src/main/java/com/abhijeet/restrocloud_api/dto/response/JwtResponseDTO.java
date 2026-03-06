package com.abhijeet.restrocloud_api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponseDTO {
    private String token;
    private String email;
}