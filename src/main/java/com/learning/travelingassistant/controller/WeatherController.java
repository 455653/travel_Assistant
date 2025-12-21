package com.learning.travelingassistant.controller;

import com.learning.travelingassistant.common.Result;
import com.learning.travelingassistant.dto.WeatherDTO;
import com.learning.travelingassistant.entity.Attraction;
import com.learning.travelingassistant.entity.City;
import com.learning.travelingassistant.mapper.AttractionMapper;
import com.learning.travelingassistant.mapper.CityMapper;
import com.learning.travelingassistant.service.AmapWeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 天气控制器
 */
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    private AmapWeatherService amapWeatherService;

    @Autowired
    private AttractionMapper attractionMapper;

    @Autowired
    private CityMapper cityMapper;

    /**
     * 根据景点ID获取该景点所在城市的天气预报
     * GET /api/weather/forecast?attractionId={id}
     */
    @GetMapping("/forecast")
    public Result<List<WeatherDTO>> getWeatherForecast(@RequestParam Long attractionId) {
        try {
            // 1. 根据景点ID查询景点信息
            Attraction attraction = attractionMapper.findById(attractionId);
            if (attraction == null) {
                logger.warn("景点不存在: {}", attractionId);
                return Result.error("景点不存在");
            }

            // 2. 根据城市ID查询城市信息
            City city = cityMapper.findById(attraction.getCityId());
            if (city == null) {
                logger.warn("城市信息不存在: {}", attraction.getCityId());
                return Result.error("城市信息不存在");
            }

            // 3. 检查adcode是否存在
            if (city.getAdcode() == null || city.getAdcode().isEmpty()) {
                logger.warn("城市【{}】的adcode为空，无法获取天气", city.getName());
                return Result.success(List.of()); // 返回空列表，不报错
            }

            logger.info("获取城市【{}】(adcode={})的天气预报", city.getName(), city.getAdcode());

            // 4. 调用天气服务获取天气预报
            List<WeatherDTO> weatherList = amapWeatherService.getWeatherForecast(city.getAdcode());

            return Result.success(weatherList);

        } catch (Exception e) {
            logger.error("获取天气信息失败: {}", e.getMessage(), e);
            return Result.error("获取天气信息失败");
        }
    }
}
