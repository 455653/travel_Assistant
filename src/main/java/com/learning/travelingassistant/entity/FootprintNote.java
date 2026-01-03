package com.learning.travelingassistant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FootprintNote {
    private Long id;
    private Long footprintId;
    private Long authorId;
    private String content;
    private LocalDateTime createTime;

    private String authorUsername;
}