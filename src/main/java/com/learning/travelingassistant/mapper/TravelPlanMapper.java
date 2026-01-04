package com.learning.travelingassistant.mapper;

import com.learning.travelingassistant.entity.TravelPlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TravelPlanMapper {

    void insert(TravelPlan travelPlan);

    void updateContent(@Param("id") Long id, @Param("content") String content, @Param("title") String title);

    List<TravelPlan> selectByUserId(@Param("userId") Long userId);

    TravelPlan selectById(@Param("id") Long id);
}