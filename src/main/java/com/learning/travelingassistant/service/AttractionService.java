package com.learning.travelingassistant.service;

import com.learning.travelingassistant.dto.AttractionDetailDTO;
import com.learning.travelingassistant.entity.Attraction;

import java.util.List;

public interface AttractionService {
    List<Attraction> getTopAttractionsByCityId(Long cityId, Integer limit);
    
    AttractionDetailDTO getAttractionDetail(Long id);
    
    /**
     * 获取全网热门景点（按 view_count 倒序）
     */
    List<Attraction> getHotAttractions(Integer limit);
    
    /**
     * 根据关键字搜索景点
     */
    List<Attraction> searchAttractions(String keyword);
}
