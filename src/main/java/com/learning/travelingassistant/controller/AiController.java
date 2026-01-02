
package com.learning.travelingassistant.controller;

import com.learning.travelingassistant.common.Result;
import com.learning.travelingassistant.entity.Attraction;
import com.learning.travelingassistant.mapper.AttractionMapper;
import com.learning.travelingassistant.service.DeepSeekService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private static final Logger logger = LoggerFactory.getLogger(AiController.class);

    @Autowired
    private DeepSeekService deepSeekService;

    @Autowired
    private AttractionMapper attractionMapper;

    @GetMapping("/generate-guide")
    public Result<String> generateGuide(
            @RequestParam("attractionId") Long attractionId,
            @RequestParam("people") Integer people,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        
        try {
            if (attractionId == null || people == null || people <= 0) {
                return Result.error("参数错误：景点ID和人数不能为空");
            }

            if (startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()) {
                return Result.error("参数错误：请选择出游日期");
            }

            Attraction attraction = attractionMapper.findById(attractionId);
            if (attraction == null) {
                return Result.error("景点不存在");
            }

            logger.info("开始生成AI攻略: 景点ID={}, 景点名={}, 人数={}, 日期={} 到 {}",
                        attractionId, attraction.getName(), people, startDate, endDate);

            String guide = deepSeekService.generateGuide(
                attraction.getName(),
                people,
                startDate,
                endDate
            );

            return Result.success(guide);

        } catch (Exception e) {
            logger.error("生成AI攻略失败: {}", e.getMessage(), e);
            return Result.error("生成攻略失败，请稍后再试");
        }
    }
}