package com.learning.travelingassistant.service;

import com.learning.travelingassistant.dto.PlanSaveDTO;
import com.learning.travelingassistant.entity.TravelPlan;
import com.learning.travelingassistant.mapper.TravelPlanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TravelPlanService {

    private static final Logger logger = LoggerFactory.getLogger(TravelPlanService.class);

    @Autowired
    private TravelPlanMapper travelPlanMapper;

    public Long savePlan(Long userId, PlanSaveDTO dto) {
        TravelPlan plan = new TravelPlan();
        plan.setUserId(userId);
        plan.setTitle(dto.getTitle());
        plan.setDestination(dto.getDestination());
        plan.setStartDate(LocalDate.parse(dto.getStartDate()));
        plan.setEndDate(LocalDate.parse(dto.getEndDate()));
        plan.setPeopleCount(dto.getPeopleCount());
        plan.setBudgetType(dto.getBudgetType());
        plan.setContent(dto.getContent());

        travelPlanMapper.insert(plan);
        logger.info("保存旅行规划成功，ID: {}, 用户: {}", plan.getId(), userId);
        return plan.getId();
    }

    public void updatePlan(Long planId, String title, String content) {
        travelPlanMapper.updateContent(planId, content, title);
        logger.info("更新旅行规划成功，ID: {}", planId);
    }

    public List<TravelPlan> getUserPlans(Long userId) {
        return travelPlanMapper.selectByUserId(userId);
    }

    public TravelPlan getPlanById(Long planId) {
        return travelPlanMapper.selectById(planId);
    }
}