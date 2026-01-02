package com.learning.travelingassistant.controller;

import com.learning.travelingassistant.common.Result;
import com.learning.travelingassistant.entity.Comment;
import com.learning.travelingassistant.mapper.CommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentMapper commentMapper;

    @PostMapping
    public Result<String> addComment(@RequestBody Comment comment) {
        try {
            if (comment.getAttractionId() == null) {
                return Result.error("景点ID不能为空");
            }

            if (comment.getUserId() == null) {
                return Result.error("用户ID不能为空");
            }

            if (comment.getContent() == null || comment.getContent().trim().isEmpty()) {
                return Result.error("评论内容不能为空");
            }

            logger.info("接收评论数据: 景点ID={}, 用户ID={}, 内容长度={}, 图片={}", 
                comment.getAttractionId(), 
                comment.getUserId(), 
                comment.getContent().length(),
                comment.getImageUrl() != null ? "有图片" : "无图片");

            int rows = commentMapper.insert(comment);
            
            if (rows > 0) {
                logger.info("评论插入成功");
                return Result.success("评论发表成功");
            } else {
                logger.error("评论插入失败，影响行数为0");
                return Result.error("发表评论失败");
            }

        } catch (Exception e) {
            logger.error("发表评论异常: {}", e.getMessage(), e);
            return Result.error("发表评论失败: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public Result<List<Map<String, Object>>> getCommentList(@RequestParam Long attractionId) {
        try {
            if (attractionId == null) {
                return Result.error("景点ID不能为空");
            }

            logger.info("获取景点评论列表: attractionId={}", attractionId);

            List<Map<String, Object>> comments = commentMapper.selectByAttractionIdWithUser(attractionId);

            logger.info("查询到 {} 条评论", comments.size());

            return Result.success(comments);

        } catch (Exception e) {
            logger.error("获取评论列表失败: {}", e.getMessage(), e);
            return Result.error("获取评论失败");
        }
    }
}