package com.learning.travelingassistant.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.travelingassistant.dto.WeatherDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 高德地图天气服务
 */
@Service
public class AmapWeatherService {

    private static final Logger logger = LoggerFactory.getLogger(AmapWeatherService.class);

    @Value("${amap.key}")
    private String amapKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 获取指定城市的天气预报
     * @param adcode 城市行政区划代码
     * @return 天气预报列表
     */
    public List<WeatherDTO> getWeatherForecast(String adcode) {
        if (adcode == null || adcode.isEmpty()) {
            logger.warn("adcode为空，无法获取天气信息");
            return Collections.emptyList();
        }

        try {
            // 构建请求URL
            String url = String.format(
                "https://restapi.amap.com/v3/weather/weatherInfo?city=%s&key=%s&extensions=all",
                adcode, amapKey
            );

            logger.info("请求高德天气API: city={}", adcode);
            logger.info("请求URL: {}", url);

            // 调用API
            String response = restTemplate.getForObject(url, String.class);
            logger.info("高德API响应: {}", response);

            // 解析响应
            return parseWeatherResponse(response);

        } catch (Exception e) {
            logger.error("获取天气数据失败: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    /**
     * 解析高德天气API响应
     */
    private List<WeatherDTO> parseWeatherResponse(String response) {
        List<WeatherDTO> weatherList = new ArrayList<>();

        try {
            JsonNode rootNode = objectMapper.readTree(response);

            // 检查状态码
            String status = rootNode.get("status").asText();
            if (!"1".equals(status)) {
                String errorMsg = rootNode.has("info") ? rootNode.get("info").asText() : "未知错误";
                logger.error("高德天气API返回错误 - status: {}, info: {}", status, errorMsg);
                return weatherList;
            }

            // 解析forecasts数组
            JsonNode forecasts = rootNode.get("forecasts");
            if (forecasts != null && forecasts.isArray() && forecasts.size() > 0) {
                JsonNode castsArray = forecasts.get(0).get("casts");

                if (castsArray != null && castsArray.isArray()) {
                    for (JsonNode castNode : castsArray) {
                        WeatherDTO weather = new WeatherDTO();
                        weather.setDate(castNode.get("date").asText());
                        weather.setDayWeather(castNode.get("dayweather").asText());
                        weather.setNightWeather(castNode.get("nightweather").asText());
                        weather.setDayTemp(castNode.get("daytemp").asText());
                        weather.setNightTemp(castNode.get("nighttemp").asText());
                        weather.setWeek(castNode.get("week").asText());

                        weatherList.add(weather);
                    }
                    logger.info("成功解析{}条天气数据", weatherList.size());
                } else {
                    logger.warn("casts数组为空或不是数组格式");
                }
            } else {
                logger.warn("forecasts数据为空或格式不正确");
            }

        } catch (Exception e) {
            logger.error("解析天气数据失败: {}", e.getMessage(), e);
        }

        logger.info("最终返回{}条天气数据", weatherList.size());
        return weatherList;
    }
}
