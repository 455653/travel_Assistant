package com.learning.travelingassistant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanRequestDTO {
    private String destination;
    private String startDate;
    private String endDate;
    private Integer peopleCount;
    private String transportType;
    private String budgetType;
    private String preferences;
}