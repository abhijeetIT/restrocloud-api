package com.abhijeet.restrocloud_api.service.impl;

import com.abhijeet.restrocloud_api.dto.request.DiningTableRequestDTO;
import com.abhijeet.restrocloud_api.dto.response.DiningTableResponseDTO;
import com.abhijeet.restrocloud_api.entity.DiningTable;
import com.abhijeet.restrocloud_api.entity.Restaurant;
import com.abhijeet.restrocloud_api.exception.BadRequestException;
import com.abhijeet.restrocloud_api.exception.DuplicateResourceException;
import com.abhijeet.restrocloud_api.exception.ResourceNotFoundException;
import com.abhijeet.restrocloud_api.repository.DiningTableRepository;
import com.abhijeet.restrocloud_api.service.DiningTableService;
import com.abhijeet.restrocloud_api.service.RestaurantService;
import com.abhijeet.restrocloud_api.util.RestaurantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class DiningTableServiceImpl implements DiningTableService {

    private DiningTableResponseDTO mapToResponse(DiningTable diningTable){
        return DiningTableResponseDTO.builder()
                .id(diningTable.getId())
                .tableNumber(diningTable.getTableNumber())
                .capacity(diningTable.getCapacity())
                .restaurantId(diningTable.getRestaurant().getId())
                .status(diningTable.getStatus())
                .build();
    }

    @Autowired
    private DiningTableRepository diningTableRepository;

    @Autowired
    private RestaurantUtil restaurantUtil;

    @Autowired
    private RestaurantService restaurantService;


    @Override
    public DiningTableResponseDTO addTable(DiningTableRequestDTO tableRequestDTO) {
        Long id = restaurantUtil.getLoggedInRestaurantId();

        Restaurant restaurant = restaurantService.getRestaurant(id);

        //this cheak the table exists
        if(diningTableRepository.existsByRestaurantIdAndTableNumber(id, tableRequestDTO.getTableNumber())){
            throw new DuplicateResourceException("Table number already exists");
        }

        return mapToResponse(diningTableRepository.save(DiningTable.builder()
                .tableNumber(tableRequestDTO.getTableNumber())
                .capacity(tableRequestDTO.getCapacity())
                .restaurant(restaurant)
                .build()
        ));
    }

    @Override
    public List<DiningTableResponseDTO> getAllTableList() {
        Long id = restaurantUtil.getLoggedInRestaurantId();

        List<DiningTable> tables = diningTableRepository.findByRestaurantId(id);

        return tables.stream()
                .map(this::mapToResponse)
                .toList();

    }

    @Override
    public DiningTableResponseDTO getTableByTableNumber(Integer tableNumber) {

        Long restaurantId = restaurantUtil.getLoggedInRestaurantId();

        DiningTable table = diningTableRepository
                .findByRestaurantIdAndTableNumber(restaurantId, tableNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Table not found with number " + tableNumber));

        return mapToResponse(table);
    }

    @Override
    public void deleteById(Long tableId) {

        Long restaurantId = restaurantUtil.getLoggedInRestaurantId();

        DiningTable table = diningTableRepository
                .findByIdAndRestaurantId(tableId, restaurantId) //this funtion return object if table exists with perticular restaurat id and table id
                .orElseThrow(() ->
                        new BadRequestException("Table not found in your restaurant"));

        diningTableRepository.delete(table);
    }
}
