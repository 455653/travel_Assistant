package com.learning.travelingassistant.controller;

import com.learning.travelingassistant.common.Result;
import com.learning.travelingassistant.entity.Province;
import com.learning.travelingassistant.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/provinces")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    /**
     * 获取所有省份列表
     * GET /api/provinces
     */
    @GetMapping
    public Result<List<Province>> getAllProvinces() {
        List<Province> provinces = provinceService.getAllProvinces();
        return Result.success(provinces);
    }
}
