package com.learning.travelingassistant.controller;

import com.learning.travelingassistant.common.Result;
import com.learning.travelingassistant.dto.PlanRequestDTO;
import com.learning.travelingassistant.dto.PlanSaveDTO;
import com.learning.travelingassistant.entity.TravelPlan;
import com.learning.travelingassistant.service.AiPlanningService;
import com.learning.travelingassistant.service.ExportService;
import com.learning.travelingassistant.service.TravelPlanService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/plan")
public class TravelPlanController {

    private static final Logger logger = LoggerFactory.getLogger(TravelPlanController.class);

    @Autowired
    private AiPlanningService aiPlanningService;

    @Autowired
    private TravelPlanService travelPlanService;

    @Autowired
    private ExportService exportService;

    @PostMapping("/generate")
    public Result<String> generatePlan(@RequestBody PlanRequestDTO request) {
        try {
            logger.info("收到生成旅行规划请求: {}", request);
            String plan = aiPlanningService.generatePlan(request);
            return Result.success(plan);
        } catch (Exception e) {
            logger.error("生成旅行规划失败: {}", e.getMessage(), e);
            return Result.error("生成旅行规划失败: " + e.getMessage());
        }
    }

    @PostMapping("/save")
    public Result<Long> savePlan(@RequestParam Long userId, @RequestBody PlanSaveDTO dto) {
        try {
            logger.info("保存旅行规划，用户ID: {}, 标题: {}", userId, dto.getTitle());
            Long planId = travelPlanService.savePlan(userId, dto);
            return Result.success("保存成功", planId);
        } catch (Exception e) {
            logger.error("保存旅行规划失败: {}", e.getMessage(), e);
            return Result.error("保存失败: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public Result<Void> updatePlan(@RequestParam Long planId,
                                    @RequestParam String title,
                                    @RequestParam String content) {
        try {
            travelPlanService.updatePlan(planId, title, content);
            return Result.success("更新成功", null);
        } catch (Exception e) {
            logger.error("更新旅行规划失败: {}", e.getMessage(), e);
            return Result.error("更新失败: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public Result<List<TravelPlan>> getUserPlans(@RequestParam Long userId) {
        try {
            List<TravelPlan> plans = travelPlanService.getUserPlans(userId);
            return Result.success(plans);
        } catch (Exception e) {
            logger.error("获取用户旅行规划列表失败: {}", e.getMessage(), e);
            return Result.error("获取列表失败: " + e.getMessage());
        }
    }

    @GetMapping("/export")
    public void exportPlan(@RequestParam Long id, HttpServletResponse response) {
        try {
            TravelPlan plan = travelPlanService.getPlanById(id);
            if (plan == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("旅行规划不存在");
                return;
            }

            byte[] wordBytes = exportService.exportPlanToWord(plan.getTitle(), plan.getContent());

            String filename = URLEncoder.encode(plan.getTitle(), StandardCharsets.UTF_8) + ".docx";

            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            response.setContentLength(wordBytes.length);

            try (OutputStream out = response.getOutputStream()) {
                out.write(wordBytes);
                out.flush();
            }

            logger.info("成功导出旅行规划，ID: {}, 标题: {}", id, plan.getTitle());

        } catch (IOException e) {
            logger.error("导出旅行规划失败: {}", e.getMessage(), e);
            try {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("导出失败");
            } catch (IOException ex) {
                logger.error("写入错误响应失败", ex);
            }
        }
    }
}