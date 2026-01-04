package com.learning.travelingassistant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanSaveDTO {
    private String title;
    private String destination;
    private String startDate;
    private String endDate;
    private Integer peopleCount;
    private String budgetType;
    private String content;
}