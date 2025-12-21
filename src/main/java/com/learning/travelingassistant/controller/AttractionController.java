package com.learning.travelingassistant.controller;

import com.learning.travelingassistant.common.Result;
import com.learning.travelingassistant.dto.AttractionDetailDTO;
import com.learning.travelingassistant.entity.Attraction;
import com.learning.travelingassistant.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attractions")
public class AttractionController {

    @Autowired
    private AttractionService attractionService;

    /**
     * 根据城市ID获取Top N热门景点（按view_count倒序）
     * GET /api/attractions/top/{cityId}?limit=5
     */
    @GetMapping("/top/{cityId}")
    public Result<List<Attraction>> getTopAttractions(
            @PathVariable Long cityId,
            @RequestParam(defaultValue = "5") Integer limit) {
        List<Attraction> attractions = attractionService.getTopAttractionsByCityId(cityId, limit);
        return Result.success(attractions);
    }

    /**
     * 获取特定景点的详情（包含该景点的评论列表）
     * GET /api/attractions/{id}/detail
     */
    @GetMapping("/{id}/detail")
    public Result<AttractionDetailDTO> getAttractionDetail(@PathVariable Long id) {
        AttractionDetailDTO detail = attractionService.getAttractionDetail(id);
        return Result.success(detail);
    }

    /**
     * 获取全网热门景点（按 view_count 倒序，默认返回 Top 9）
     * GET /api/attractions/hot?limit=9
     */
    @GetMapping("/hot")
    public Result<List<Attraction>> getHotAttractions(
            @RequestParam(defaultValue = "9") Integer limit) {
        List<Attraction> attractions = attractionService.getHotAttractions(limit);
        return Result.success(attractions);
    }
}
