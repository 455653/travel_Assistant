package com.learning.travelingassistant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 天气预报数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDTO {
    /**
     * 日期 (格式: 2024-12-21)
     */
    private String date;
    
    /**
     * 白天天气现象
     */
    private String dayWeather;
    
    /**
     * 夜晚天气现象
     */
    private String nightWeather;
    
    /**
     * 白天温度
     */
    private String dayTemp;
    
    /**
     * 夜晚温度
     */
    private String nightTemp;
    
    /**
     * 星期几
     */
    private String week;
}
