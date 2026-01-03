package com.learning.travelingassistant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FootprintItemDTO {
    private String type;  // PHOTO 或 NOTE
    private String content;  // 图片URL或文字内容
    private String authorName;  // 作者/上传者名称
    private LocalDateTime createTime;  // 创建时间
    private Long itemId;  // 照片ID或笔记ID（用于删除）
}