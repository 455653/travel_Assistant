package com.learning.travelingassistant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FootprintCollaborator {
    private Long id;
    private Long footprintId;
    private Long userId;
    private LocalDateTime createTime;

    private String username;
}