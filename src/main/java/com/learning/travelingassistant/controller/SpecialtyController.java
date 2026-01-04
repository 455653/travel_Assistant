package com.learning.travelingassistant.controller;

import com.learning.travelingassistant.common.Result;
import com.learning.travelingassistant.entity.LocalSpecialty;
import com.learning.travelingassistant.service.SpecialtyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialty")
public class SpecialtyController {

    private static final Logger logger = LoggerFactory.getLogger(SpecialtyController.class);

    @Autowired
    private SpecialtyService specialtyService;

    @GetMapping("/list")
    public Result<List<LocalSpecialty>> getSpecialtiesByCityId(@RequestParam Long cityId) {
        try {
            logger.info("查询城市特产，城市ID: {}", cityId);
            List<LocalSpecialty> specialties = specialtyService.getSpecialtiesByCityId(cityId);
            return Result.success(specialties);
        } catch (Exception e) {
            logger.error("查询城市特产失败: {}", e.getMessage(), e);
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public Result<List<LocalSpecialty>> getAllSpecialties(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize) {
        try {
            logger.info("查询所有特产，页码: {}, 每页数量: {}", page, pageSize);
            List<LocalSpecialty> specialties = specialtyService.getAllSpecialties(page, pageSize);
            return Result.success(specialties);
        } catch (Exception e) {
            logger.error("查询所有特产失败: {}", e.getMessage(), e);
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public Result<List<LocalSpecialty>> searchSpecialties(@RequestParam String keyword) {
        try {
            logger.info("搜索特产，关键词: {}", keyword);
            List<LocalSpecialty> specialties = specialtyService.searchSpecialties(keyword);
            return Result.success(specialties);
        } catch (Exception e) {
            logger.error("搜索特产失败: {}", e.getMessage(), e);
            return Result.error("搜索失败: " + e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result<Long> addSpecialty(@RequestBody LocalSpecialty specialty) {
        try {
            logger.info("添加特产: {}", specialty.getName());
            specialtyService.addSpecialty(specialty);
            return Result.success("添加成功", specialty.getId());
        } catch (Exception e) {
            logger.error("添加特产失败: {}", e.getMessage(), e);
            return Result.error("添加失败: " + e.getMessage());
        }
    }

    @GetMapping("/ai/generate-text")
    public Result<String> generateMarketingText(
            @RequestParam String name,
            @RequestParam String features) {
        try {
            logger.info("生成营销文案，特产: {}, 特点: {}", name, features);
            String text = specialtyService.generateMarketingText(name, features);
            return Result.success(text);
        } catch (Exception e) {
            logger.error("生成营销文案失败: {}", e.getMessage(), e);
            return Result.error("生成失败: " + e.getMessage());
        }
    }
}