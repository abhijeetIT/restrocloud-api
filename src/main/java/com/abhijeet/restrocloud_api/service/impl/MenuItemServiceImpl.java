package com.abhijeet.restrocloud_api.service.impl;

import com.abhijeet.restrocloud_api.dto.request.MenuItemRequestDTO;
import com.abhijeet.restrocloud_api.dto.response.MenuItemResponseDTO;
import com.abhijeet.restrocloud_api.entity.MenuItem;
import com.abhijeet.restrocloud_api.entity.Restaurant;
import com.abhijeet.restrocloud_api.enums.Category;
import com.abhijeet.restrocloud_api.exception.DuplicateResourceException;
import com.abhijeet.restrocloud_api.exception.ResourceNotFoundException;
import com.abhijeet.restrocloud_api.mapper.MenuItemMapper;
import com.abhijeet.restrocloud_api.repository.MenuItemRepository;
import com.abhijeet.restrocloud_api.repository.RestaurantRepository;
import com.abhijeet.restrocloud_api.service.MenuItemService;
import com.abhijeet.restrocloud_api.util.RestaurantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    @Autowired
    private RestaurantUtil restaurantUtil;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuItemMapper menuItemMapper;

    @Override
    @Transactional
    public MenuItemResponseDTO addItem(MenuItemRequestDTO menuItemRequestDTO) {
        Long loggedRestaurantId = restaurantUtil.getLoggedInRestaurantId();

        if(menuItemRepository.existsByRestaurantIdAndName(loggedRestaurantId, menuItemRequestDTO.getName())){
            throw new DuplicateResourceException("Item already exists with this name");
        }

        Restaurant restaurant = restaurantRepository.findById(loggedRestaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found. Please login again"));

        return menuItemMapper.mapToMenuItemResponseDTO(menuItemRepository.save(MenuItem.builder()
                .name(menuItemRequestDTO.getName())
                .price(menuItemRequestDTO.getPrice())
                .description(menuItemRequestDTO.getDescription())
                .imageUrl(menuItemRequestDTO.getImageUrl())
                .category(menuItemRequestDTO.getCategory())
                .isAvailable(menuItemRequestDTO.getIsAvailable() != null ?
                        menuItemRequestDTO.getIsAvailable() : true)
                .restaurant(restaurant)
                .build()
        ));
    }

    @Override
    public List<MenuItemResponseDTO> listOfItemInRestaurant() {
        List<MenuItem> items = menuItemRepository.findByRestaurantId(restaurantUtil.getLoggedInRestaurantId());
        return items.stream()
                .map(menuItemMapper::mapToMenuItemResponseDTO)
                .toList();
    }

    @Override
    public List<MenuItemResponseDTO> listOfCategoryItemInRestaurant(String category) {
        List<MenuItem> items = menuItemRepository.findByRestaurantIdAndCategory(
                restaurantUtil.getLoggedInRestaurantId(),
                Category.valueOf(category.toUpperCase())
        );
        return items.stream()
                .map(menuItemMapper::mapToMenuItemResponseDTO)
                .toList();
    }

    @Override
    public MenuItemResponseDTO findByItemId(Long itemId) {
        Long loggedInRestaurantId = restaurantUtil.getLoggedInRestaurantId();

        MenuItem menuItem = menuItemRepository.findByIdAndRestaurantId(itemId, loggedInRestaurantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Item not found with id: " + itemId));

        return menuItemMapper.mapToMenuItemResponseDTO(menuItem);
    }

    @Override
    public MenuItemResponseDTO findByItemName(String itemName) {
        return menuItemMapper.mapToMenuItemResponseDTO(menuItemRepository.findByRestaurantIdAndName(
                        restaurantUtil.getLoggedInRestaurantId(),
                        itemName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Item not found in your restaurant: " + itemName)));
    }

    @Override
    @Transactional
    public MenuItemResponseDTO updateItem(Long itemId, MenuItemRequestDTO menuItemRequestDTO) {
        Long loggedInRestaurantId = restaurantUtil.getLoggedInRestaurantId();

        // Find existing item by ID
        MenuItem existingItem = menuItemRepository.findByIdAndRestaurantId(itemId, loggedInRestaurantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Item not found with id: " + itemId));

        // Check if the new name is different and already exists
        if (!existingItem.getName().equals(menuItemRequestDTO.getName()) &&
                menuItemRepository.existsByRestaurantIdAndName(
                        loggedInRestaurantId,
                        menuItemRequestDTO.getName())) {
            throw new DuplicateResourceException("Item with name '" +
                    menuItemRequestDTO.getName() + "' already exists in your restaurant");
        }

        // Update fields
        existingItem.setName(menuItemRequestDTO.getName());
        existingItem.setPrice(menuItemRequestDTO.getPrice());
        existingItem.setDescription(menuItemRequestDTO.getDescription());
        existingItem.setImageUrl(menuItemRequestDTO.getImageUrl());
        existingItem.setCategory(menuItemRequestDTO.getCategory());

        if (menuItemRequestDTO.getIsAvailable() != null) {
            existingItem.setIsAvailable(menuItemRequestDTO.getIsAvailable());
        }

        return menuItemMapper.mapToMenuItemResponseDTO(menuItemRepository.save(existingItem));
    }

    @Override
    @Transactional
    public void deleteItemById(Long itemId) {
        Long loggedInRestaurantId = restaurantUtil.getLoggedInRestaurantId();

        MenuItem item = menuItemRepository.findByIdAndRestaurantId(itemId, loggedInRestaurantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Item not found with id: " + itemId));

        menuItemRepository.delete(item);
    }

    @Override
    @Transactional
    public void deleteItemByName(String itemName) {
        Long loggedInRestaurantId = restaurantUtil.getLoggedInRestaurantId();

        MenuItem item = menuItemRepository.findByRestaurantIdAndName(loggedInRestaurantId, itemName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Item not found in your restaurant: " + itemName));

        menuItemRepository.delete(item);
    }
}