package com.learning.travelingassistant.mapper;

import com.learning.travelingassistant.entity.Attraction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttractionMapper {
    List<Attraction> findTopByCityId(@Param("cityId") Long cityId, @Param("limit") Integer limit);
    
    Attraction findById(@Param("id") Long id);
    
    /**
     * 获取全网热门景点（按 view_count 倒序）
     */
    List<Attraction> findHotAttractions(@Param("limit") Integer limit);
    
    /**
     * 根据关键字搜索景点（支持景点名、城市名、省份名模糊匹配）
     */
    List<Attraction> searchAttractions(@Param("keyword") String keyword);
}
