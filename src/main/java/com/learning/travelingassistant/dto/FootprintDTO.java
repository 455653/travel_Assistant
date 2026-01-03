package com.learning.travelingassistant.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FootprintDTO {
    private Long provinceId;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Long> friendIds;
}