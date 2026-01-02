package com.learning.travelingassistant.mapper;

import com.learning.travelingassistant.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> findByAttractionId(@Param("attractionId") Long attractionId);
    
    int insert(Comment comment);
}
