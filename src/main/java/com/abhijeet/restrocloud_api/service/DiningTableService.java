package com.abhijeet.restrocloud_api.service;

import com.abhijeet.restrocloud_api.dto.request.DiningTableRequestDTO;
import com.abhijeet.restrocloud_api.dto.response.DiningTableResponseDTO;

import java.util.List;

public interface DiningTableService {
    DiningTableResponseDTO addTable(DiningTableRequestDTO tableRequestDTO);
    List<DiningTableResponseDTO> getAllTableList();

    DiningTableResponseDTO getTableByTableNumber(Integer tableNumber);

    void deleteById(Long tableId);

    DiningTableResponseDTO updateTable(Long tableId,DiningTableRequestDTO diningTableRequestDTO);
}
