package com.abhijeet.restrocloud_api.service.impl;

import com.abhijeet.restrocloud_api.dto.request.DiningTableRequestDTO;
import com.abhijeet.restrocloud_api.dto.response.DiningTableResponseDTO;
import com.abhijeet.restrocloud_api.entity.DiningTable;
import com.abhijeet.restrocloud_api.entity.Restaurant;
import com.abhijeet.restrocloud_api.exception.BadRequestException;
import com.abhijeet.restrocloud_api.exception.DuplicateResourceException;
import com.abhijeet.restrocloud_api.exception.ResourceNotFoundException;
import com.abhijeet.restrocloud_api.mapper.DiningTableMapper;
import com.abhijeet.restrocloud_api.repository.DiningTableRepository;
import com.abhijeet.restrocloud_api.service.DiningTableService;
import com.abhijeet.restrocloud_api.service.RestaurantService;
import com.abhijeet.restrocloud_api.util.RestaurantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DiningTableServiceImpl implements DiningTableService {

    @Autowired
    private DiningTableRepository diningTableRepository;

    @Autowired
    private RestaurantUtil restaurantUtil;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private DiningTableMapper diningTableMapper;


    @Override
    public DiningTableResponseDTO addTable(DiningTableRequestDTO tableRequestDTO) {
        Long id = restaurantUtil.getLoggedInRestaurantId();

        Restaurant restaurant = restaurantService.getRestaurant(id);

        //this cheak the table exists
        if (diningTableRepository.existsByRestaurantIdAndTableNumber(id, tableRequestDTO.getTableNumber())) {
            throw new DuplicateResourceException("Table number already exists");
        }

        return diningTableMapper.mapToDiningTableResponseDTO(diningTableRepository.save(DiningTable.builder()
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
                .map(diningTableMapper::mapToDiningTableResponseDTO)
                .toList();

    }

    @Override
    public DiningTableResponseDTO getTableByTableNumber(Integer tableNumber) {

        Long restaurantId = restaurantUtil.getLoggedInRestaurantId();

        DiningTable table = diningTableRepository
                .findByRestaurantIdAndTableNumber(restaurantId, tableNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Table not found with number " + tableNumber));

        return diningTableMapper.mapToDiningTableResponseDTO(table);
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

    @Override
    public DiningTableResponseDTO updateTable(Long tableId, DiningTableRequestDTO dto) {

        Long restaurantId = restaurantUtil.getLoggedInRestaurantId();

        // Fetch table of this restaurant
        DiningTable table = diningTableRepository
                .findByIdAndRestaurantId(tableId, restaurantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Table not found in your restaurant"));

        // Check table number conflict
        if (diningTableRepository.existsByRestaurantIdAndTableNumberAndIdNot(
                restaurantId, dto.getTableNumber(), tableId)) {

            throw new DuplicateResourceException(
                    "Table number already exists in your restaurant");
        }

        // Update fields
        table.setTableNumber(dto.getTableNumber());
        table.setCapacity(dto.getCapacity());

        if (dto.getStatus() != null) {
            table.setStatus(dto.getStatus());
        }

        DiningTable updated = diningTableRepository.save(table);

        return diningTableMapper.mapToDiningTableResponseDTO(updated);
    }
}
