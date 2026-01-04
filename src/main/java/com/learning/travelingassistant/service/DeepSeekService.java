
package com.learning.travelingassistant.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class DeepSeekService {

    private static final Logger logger = LoggerFactory.getLogger(DeepSeekService.class);

    @Value("${deepseek.api-key}")
    private String apiKey;

    @Value("${deepseek.base-url}")
    private String baseUrl;

    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public DeepSeekService() {
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public String generateGuide(String attractionName, int peopleCount, String startDate, String endDate) {
        try {
            String systemPrompt = "你是一个专业的资深导游，擅长规划中国旅游路线。";
            String userPrompt = String.format(
                "请为我规划一份去【%s】的旅游攻略。\n" +
                "人数：%d人。\n" +
                "时间：%s 到 %s。\n" +
                "请包含以下内容：\n" +
                "1. 每日详细路线推荐。\n" +
                "2. 必打卡的具体景点。\n" +
                "3. 酒店住宿区域推荐。\n" +
                "4. 每日三餐美食推荐（特色菜）。\n" +
                "请使用 Markdown 格式输出，保持条理清晰，语气热情。",
                attractionName, peopleCount, startDate, endDate
            );

            String requestBody = String.format(
                "{\"model\":\"deepseek-chat\",\"messages\":[" +
                "{\"role\":\"system\",\"content\":\"%s\"}," +
                "{\"role\":\"user\",\"content\":\"%s\"}" +
                "],\"stream\":false}",
                escapeJson(systemPrompt),
                escapeJson(userPrompt)
            );

            Request request = new Request.Builder()
                    .url(baseUrl + "/chat/completions")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .post(RequestBody.create(requestBody, MediaType.parse("application/json")))
                    .build();

            logger.info("调用 DeepSeek API 生成攻略: 景点={}, 人数={}, 日期={} 到 {}",
                        attractionName, peopleCount, startDate, endDate);

            try (Response response = httpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    logger.error("DeepSeek API 调用失败: HTTP {}", response.code());
                    return "抱歉，AI 服务暂时不可用，请稍后再试。";
                }

                String responseBody = response.body().string();
                logger.info("DeepSeek API 响应: {}", responseBody);

                JsonNode rootNode = objectMapper.readTree(responseBody);
                JsonNode choices = rootNode.get("choices");

                if (choices != null && choices.isArray() && choices.size() > 0) {
                    JsonNode message = choices.get(0).get("message");
                    if (message != null && message.has("content")) {
                        String content = message.get("content").asText();
                        logger.info("成功生成攻略，长度: {} 字符", content.length());
                        return content;
                    }
                }

                logger.error("DeepSeek API 响应格式异常");
                return "抱歉，无法生成攻略，请重试。";
            }

        } catch (IOException e) {
            logger.error("调用 DeepSeek API 异常: {}", e.getMessage(), e);
            return "网络连接失败，请检查网络后重试。";
        } catch (Exception e) {
            logger.error("生成攻略时发生未知错误: {}", e.getMessage(), e);
            return "系统错误，请稍后再试。";
        }
    }

    private String escapeJson(String text) {
        return text.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }

    public String parseExpenseText(String userInput) {
        try {
            String systemPrompt = "你是一个智能记账助手。用户会输入一段自然语言描述消费，你需要提取关键信息。";
            String userPrompt = String.format(
                "请从以下文字中提取消费信息：\"%s\"\n\n" +
                "要求：\n" +
                "1. 提取金额（amount，数字类型）\n" +
                "2. 判断分类（category），只能从以下选项中选择：餐饮、交通、住宿、门票、购物、其他\n" +
                "3. 提取或推测日期（date，格式：yyyy-MM-dd，如果没有明确日期则使用今天：%s）\n" +
                "4. 提取描述（description）\n\n" +
                "**重要**：只返回纯JSON格式，不要有任何其他文字。格式示例：\n" +
                "{\"amount\":300,\"category\":\"餐饮\",\"date\":\"2026-01-04\",\"description\":\"中午吃火锅\"}",
                userInput,
                java.time.LocalDate.now().toString()
            );

            String requestBody = String.format(
                "{\"model\":\"deepseek-chat\",\"messages\":[" +
                "{\"role\":\"system\",\"content\":\"%s\"}," +
                "{\"role\":\"user\",\"content\":\"%s\"}" +
                "],\"stream\":false}",
                escapeJson(systemPrompt),
                escapeJson(userPrompt)
            );

            Request request = new Request.Builder()
                    .url(baseUrl + "/chat/completions")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .post(RequestBody.create(requestBody, MediaType.parse("application/json")))
                    .build();

            logger.info("调用 DeepSeek API 解析消费文本: {}", userInput);

            try (Response response = httpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    logger.error("DeepSeek API 调用失败: HTTP {}", response.code());
                    return null;
                }

                String responseBody = response.body().string();
                logger.info("DeepSeek API 响应: {}", responseBody);

                JsonNode rootNode = objectMapper.readTree(responseBody);
                JsonNode choices = rootNode.get("choices");

                if (choices != null && choices.isArray() && choices.size() > 0) {
                    JsonNode message = choices.get(0).get("message");
                    if (message != null && message.has("content")) {
                        String content = message.get("content").asText().trim();
                        logger.info("AI 解析结果: {}", content);
                        
                        if (content.startsWith("{")) {
                            return content;
                        }
                    }
                }

                logger.error("DeepSeek API 响应格式异常");
                return null;
            }

        } catch (IOException e) {
            logger.error("调用 DeepSeek API 异常: {}", e.getMessage(), e);
            return null;
        } catch (Exception e) {
            logger.error("解析消费文本时发生未知错误: {}", e.getMessage(), e);
            return null;
        }
    }

    public String callDeepSeekApi(String systemPrompt, String userPrompt) {
        try {
            String requestBody = String.format(
                "{\"model\":\"deepseek-chat\",\"messages\":[" +
                "{\"role\":\"system\",\"content\":\"%s\"}," +
                "{\"role\":\"user\",\"content\":\"%s\"}" +
                "],\"stream\":false}",
                escapeJson(systemPrompt),
                escapeJson(userPrompt)
            );

            Request request = new Request.Builder()
                    .url(baseUrl + "/chat/completions")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .post(RequestBody.create(requestBody, MediaType.parse("application/json")))
                    .build();

            logger.info("调用 DeepSeek API");

            try (Response response = httpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    logger.error("DeepSeek API 调用失败: HTTP {}", response.code());
                    return "抱歉，AI 服务暂时不可用，请稍后再试。";
                }

                String responseBody = response.body().string();
                logger.info("DeepSeek API 响应成功");

                JsonNode rootNode = objectMapper.readTree(responseBody);
                JsonNode choices = rootNode.get("choices");

                if (choices != null && choices.isArray() && choices.size() > 0) {
                    JsonNode message = choices.get(0).get("message");
                    if (message != null && message.has("content")) {
                        String content = message.get("content").asText();
                        logger.info("成功生成内容，长度: {} 字符", content.length());
                        return content;
                    }
                }

                logger.error("DeepSeek API 响应格式异常");
                return "抱歉，无法生成内容，请重试。";
            }

        } catch (IOException e) {
            logger.error("调用 DeepSeek API 异常: {}", e.getMessage(), e);
            return "网络连接失败，请检查网络后重试。";
        } catch (Exception e) {
            logger.error("调用 API 时发生未知错误: {}", e.getMessage(), e);
            return "系统错误，请稍后再试。";
        }
    }
}