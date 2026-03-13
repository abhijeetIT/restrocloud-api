package com.abhijeet.restrocloud_api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
}