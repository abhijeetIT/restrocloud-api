package com.abhijeet.restrocloud_api.controller;

import com.abhijeet.restrocloud_api.dto.ApiResponse;
import com.abhijeet.restrocloud_api.dto.response.SummaryResponseDTO;
import com.abhijeet.restrocloud_api.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsApiController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<?>> summary(){

        return ResponseEntity.ok(ApiResponse.builder()
                .message("Summary fetched successfully")
                .success(true)
                .data(analyticsService.getSummary())
                .build()
        );
    }
}
