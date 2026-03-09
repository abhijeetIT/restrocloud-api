package com.abhijeet.restrocloud_api.controller;

import com.abhijeet.restrocloud_api.dto.ApiResponse;
import com.abhijeet.restrocloud_api.dto.request.DiningTableRequestDTO;
import com.abhijeet.restrocloud_api.service.DiningTableService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/table")
public class DiningTableApiController {

    @Autowired
    private DiningTableService diningTableService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> addTable(@Valid @RequestBody DiningTableRequestDTO tableRequestDTO){
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Table added successfully")
                .data(diningTableService.addTable(tableRequestDTO))
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllTable(){
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("All tables fetched")
                .data(diningTableService.getAllTableList())
                .build());
    }

    @GetMapping("/{tableNumber}")
    public ResponseEntity<ApiResponse<?>> getTableByTableNumber(@PathVariable Integer tableNumber){
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Table fetched")
                .data(diningTableService.getTableByTableNumber(tableNumber))
                .build());
    }

    @DeleteMapping("/{tableId}")
    public ResponseEntity<ApiResponse<?>> deleteByTableId(@PathVariable Long tableId){
        diningTableService.deleteById(tableId);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Deleted successfully")
                .data(null)
                .build());
    }

    @PutMapping("/{tableId}")
    public ResponseEntity<ApiResponse<?>> updateTable(@PathVariable Long tableId, @RequestBody DiningTableRequestDTO diningTableRequestDTO) {

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Table updated successfully")
                        .data(diningTableService.updateTable(tableId, diningTableRequestDTO))
                        .build()
        );
    }
}
