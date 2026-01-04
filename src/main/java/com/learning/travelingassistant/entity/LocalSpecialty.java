package com.learning.travelingassistant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalSpecialty {
    private Long id;
    private Long cityId;
    private String name;
    private String type;
    private String imageUrl;
    private String description;
    private String seasonalMonth;
    private String farmerContact;
    private LocalDateTime createTime;
}