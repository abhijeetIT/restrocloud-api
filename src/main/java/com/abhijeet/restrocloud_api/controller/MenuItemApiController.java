package com.abhijeet.restrocloud_api.controller;

import com.abhijeet.restrocloud_api.dto.ApiResponse;
import com.abhijeet.restrocloud_api.dto.request.MenuItemRequestDTO;
import com.abhijeet.restrocloud_api.dto.response.MenuItemResponseDTO;
import com.abhijeet.restrocloud_api.service.MenuItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuItemApiController {

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<ApiResponse<MenuItemResponseDTO>> addItem(@Valid @RequestBody MenuItemRequestDTO menuItemRequestDTO) {
        MenuItemResponseDTO addedItem = menuItemService.addItem(menuItemRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<MenuItemResponseDTO>builder()
                        .success(true)
                        .message("Item added in menu successfully")
                        .data(addedItem)
                        .build()
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MenuItemResponseDTO>>> findAllItemInRestaurant() {
        List<MenuItemResponseDTO> items = menuItemService.listOfItemInRestaurant();

        return ResponseEntity.ok(ApiResponse.<List<MenuItemResponseDTO>>builder()
                .success(true)
                .message("Menu list fetched")
                .data(items)
                .build()
        );
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<MenuItemResponseDTO>>> getItemsByCategory(@PathVariable String category) {
        List<MenuItemResponseDTO> items = menuItemService.listOfCategoryItemInRestaurant(category);

        return ResponseEntity.ok(ApiResponse.<List<MenuItemResponseDTO>>builder()
                .success(true)
                .message("Menu items for category " + category + " fetched")
                .data(items)
                .build()
        );
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ApiResponse<MenuItemResponseDTO>> getMenuItemById(@PathVariable Long itemId) {
        MenuItemResponseDTO item = menuItemService.findByItemId(itemId);

        return ResponseEntity.ok(ApiResponse.<MenuItemResponseDTO>builder()
                .success(true)
                .message("Menu item fetched successfully")
                .data(item)
                .build()
        );
    }

    @GetMapping("/name/{itemName}")
    public ResponseEntity<ApiResponse<MenuItemResponseDTO>> getMenuItemByName(@PathVariable String itemName) {
        MenuItemResponseDTO item = menuItemService.findByItemName(itemName);

        return ResponseEntity.ok(ApiResponse.<MenuItemResponseDTO>builder()
                .success(true)
                .message("Menu item fetched successfully")
                .data(item)
                .build()
        );
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ApiResponse<MenuItemResponseDTO>> updateItem(
            @PathVariable Long itemId,
            @Valid @RequestBody MenuItemRequestDTO menuItemRequestDTO) {

        MenuItemResponseDTO updatedItem = menuItemService.updateItem(itemId, menuItemRequestDTO);

        return ResponseEntity.ok(ApiResponse.<MenuItemResponseDTO>builder()
                .success(true)
                .message("Menu item updated successfully")
                .data(updatedItem)
                .build()
        );
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<ApiResponse<Void>> deleteItemById(@PathVariable Long itemId) {
        menuItemService.deleteItemById(itemId);

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("Menu item deleted successfully")
                .build()
        );
    }

    @DeleteMapping("/name/{itemName}")
    public ResponseEntity<ApiResponse<Void>> deleteItemByName(@PathVariable String itemName) {
        menuItemService.deleteItemByName(itemName);

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("Menu item deleted successfully")
                .build()
        );
    }
}