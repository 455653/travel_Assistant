package com.learning.travelingassistant.service.impl;

import com.learning.travelingassistant.dto.AttractionDetailDTO;
import com.learning.travelingassistant.entity.Attraction;
import com.learning.travelingassistant.entity.Comment;
import com.learning.travelingassistant.mapper.AttractionMapper;
import com.learning.travelingassistant.mapper.CommentMapper;
import com.learning.travelingassistant.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttractionServiceImpl implements AttractionService {

    @Autowired
    private AttractionMapper attractionMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Attraction> getTopAttractionsByCityId(Long cityId, Integer limit) {
        return attractionMapper.findTopByCityId(cityId, limit);
    }

    @Override
    public AttractionDetailDTO getAttractionDetail(Long id) {
        Attraction attraction = attractionMapper.findById(id);
        List<Comment> comments = commentMapper.findByAttractionId(id);
        return new AttractionDetailDTO(attraction, comments);
    }
}
