package com.learning.travelingassistant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attraction {
    private Long id;
    private Long cityId;
    private String name;
    private String imageUrl;
    private String description;
    private Integer viewCount;
    private BigDecimal rating;
    private LocalDateTime createTime;
}
