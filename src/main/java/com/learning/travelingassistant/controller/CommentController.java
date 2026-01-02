
package com.learning.travelingassistant.controller;

import com.learning.travelingassistant.common.Result;
import com.learning.travelingassistant.entity.Comment;
import com.learning.travelingassistant.mapper.CommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentMapper commentMapper;

    @PostMapping
    public Result<String> addComment(@RequestBody Map<String, Object> commentData) {
        try {
            Long attractionId = Long.valueOf(commentData.get("attractionId").toString());
            Long userId = Long.valueOf(commentData.get("userId").toString());
            String content = commentData.get("content").toString();
            String imageUrl = commentData.get("imageUrl") != null ? commentData.get("imageUrl").toString() : null;

            if (content == null || content.trim().isEmpty()) {
                return Result.error("评论内容不能为空");
            }

            Comment comment = new Comment();
            comment.setAttractionId(attractionId);
            comment.setUserId(userId);
            comment.setContent(content);
            comment.setImageUrl(imageUrl);

            logger.info("添加评论: 景点ID={}, 用户ID={}, 是否有图片={}", attractionId, userId, imageUrl != null);

            return Result.success("评论发表成功");

        } catch (Exception e) {
            logger.error("发表评论失败: {}", e.getMessage(), e);
            return Result.error("发表评论失败");
        }
    }
}