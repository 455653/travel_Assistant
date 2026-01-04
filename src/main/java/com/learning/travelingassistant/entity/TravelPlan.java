package com.learning.travelingassistant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelPlan {
    private Long id;
    private Long userId;
    private String title;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer peopleCount;
    private String budgetType;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}