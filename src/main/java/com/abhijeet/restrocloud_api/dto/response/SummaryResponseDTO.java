package com.abhijeet.restrocloud_api.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SummaryResponseDTO {
      private Long totalOrdersToday;
      private Double revenueToday;
      private Double revenueThisMonth;
      private Long totalOrdersThisMonth;
      private String mostOrderedItem;
      private Long busiestTable;
      private Long cancelledOrdersToday;
}
