package com.learning.travelingassistant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class City {
    private Long id;
    private Long provinceId;
    private String name;
    private String description;
    private LocalDateTime createTime;
}
