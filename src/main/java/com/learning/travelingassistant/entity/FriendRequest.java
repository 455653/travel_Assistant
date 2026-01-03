package com.learning.travelingassistant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequest {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private String senderUsername;
    private String senderAccountId;
}