package com.abhijeet.restrocloud_api.service;

import com.abhijeet.restrocloud_api.dto.request.MenuItemRequestDTO;
import com.abhijeet.restrocloud_api.dto.response.MenuItemResponseDTO;

import java.util.List;

public interface MenuItemService {
    MenuItemResponseDTO addItem(MenuItemRequestDTO menuItemRequestDTO);
    List<MenuItemResponseDTO> listOfItemInRestaurant();
    List<MenuItemResponseDTO> listOfCategoryItemInRestaurant(String category);
    MenuItemResponseDTO findByItemName(String itemName);
    MenuItemResponseDTO findByItemId(Long itemId);
    MenuItemResponseDTO updateItem(Long itemId ,MenuItemRequestDTO menuItemRequestDTO);
    void deleteItemByName(String itemName);
    void deleteItemById(Long itemId);
}
