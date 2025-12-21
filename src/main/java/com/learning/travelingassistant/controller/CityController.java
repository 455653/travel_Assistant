package com.learning.travelingassistant.controller;

import com.learning.travelingassistant.common.Result;
import com.learning.travelingassistant.entity.City;
import com.learning.travelingassistant.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    /**
     * 根据省份ID获取城市列表
     * GET /api/cities/{provinceId}
     */
    @GetMapping("/{provinceId}")
    public Result<List<City>> getCitiesByProvinceId(@PathVariable Long provinceId) {
        List<City> cities = cityService.getCitiesByProvinceId(provinceId);
        return Result.success(cities);
    }
}
