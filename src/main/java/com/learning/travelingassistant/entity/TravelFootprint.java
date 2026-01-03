package com.learning.travelingassistant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelFootprint {
    private Long id;
    private Long creatorId;
    private Long provinceId;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createTime;
    
    private String creatorUsername;
    private String provinceName;
    private String coverImage;
}