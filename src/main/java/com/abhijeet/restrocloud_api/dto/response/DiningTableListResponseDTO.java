package com.abhijeet.restrocloud_api.dto.response;

import com.abhijeet.restrocloud_api.dto.response.DiningTableResponseDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiningTableListResponseDTO {

    private List<DiningTableResponseDTO> tables;

    private Integer totalCount; // optional but professional

}