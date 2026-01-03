package com.learning.travelingassistant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Friendship {
    private Long id;
    private Long userId;
    private Long friendId;
    private LocalDateTime createTime;

    private String friendUsername;
    private String friendAccountId;
    private String friendPhone;
    private String friendEmail;
}