package com.learning.travelingassistant.service;

import com.learning.travelingassistant.dto.PlanRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AiPlanningService {

    private static final Logger logger = LoggerFactory.getLogger(AiPlanningService.class);

    @Autowired
    private DeepSeekService deepSeekService;

    public String generatePlan(PlanRequestDTO request) {
        try {
            String systemPrompt = "你是一位专业资深的旅游规划师和导游，精通中国各地旅游景点和文化。你的任务是为用户制定详尽、实用的旅行攻略。";

            String userPrompt = buildPrompt(request);

            logger.info("构建的 Prompt: {}", userPrompt);

            return deepSeekService.callDeepSeekApi(systemPrompt, userPrompt);

        } catch (Exception e) {
            logger.error("生成旅行规划失败: {}", e.getMessage(), e);
            return "抱歉，AI 规划服务暂时不可用，请稍后再试。";
        }
    }

    private String buildPrompt(PlanRequestDTO request) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("请作为专业旅游规划师，为我制定一份详细的旅行攻略。\n\n");
        prompt.append("**旅行信息：**\n");
        prompt.append(String.format("- 目的地：%s\n", request.getDestination()));
        prompt.append(String.format("- 出行时间：%s 至 %s\n", request.getStartDate(), request.getEndDate()));
        prompt.append(String.format("- 出行人数：%d人\n", request.getPeopleCount()));

        if (request.getTransportType() != null && !request.getTransportType().isEmpty()) {
            prompt.append(String.format("- 交通方式：%s\n", request.getTransportType()));
        }

        if (request.getBudgetType() != null && !request.getBudgetType().isEmpty()) {
            prompt.append(String.format("- 预算类型：%s\n", request.getBudgetType()));
        }

        if (request.getPreferences() != null && !request.getPreferences().isEmpty()) {
            prompt.append(String.format("- 旅行偏好：%s\n", request.getPreferences()));
        }

        prompt.append("\n**攻略要求：**\n");
        prompt.append("1. **每日行程安排**：详细列出每天的游览路线，包括景点顺序、游览时长、交通方式。\n");
        prompt.append("2. **必打卡景点**：推荐当地最具代表性和特色的景点，包括开放时间、门票信息。\n");
        prompt.append("3. **交通建议**：提供从出发地到目的地的交通方式推荐，以及当地交通攻略。\n");
        prompt.append("4. **住宿推荐**：建议适合的住宿区域，根据预算类型推荐酒店档次。\n");
        prompt.append("5. **美食推荐**：推荐当地特色美食和餐厅，包括早中晚餐建议。\n");
        prompt.append("6. **旅行贴士**：包括最佳游览季节、注意事项、省钱技巧等实用信息。\n");
        prompt.append("\n**格式要求：**\n");
        prompt.append("- 请使用 Markdown 格式输出，层次清晰。\n");
        prompt.append("- 使用标题、列表、加粗等格式使内容易读。\n");
        prompt.append("- 语言要热情友好，富有感染力。\n");
        prompt.append("- 内容要详实、具体、可操作性强。\n");

        return prompt.toString();
    }
}