package com.learning.travelingassistant.service;

import com.learning.travelingassistant.entity.LocalSpecialty;
import com.learning.travelingassistant.mapper.LocalSpecialtyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialtyService {

    private static final Logger logger = LoggerFactory.getLogger(SpecialtyService.class);

    @Autowired
    private LocalSpecialtyMapper specialtyMapper;

    @Autowired
    private DeepSeekService deepSeekService;

    public void addSpecialty(LocalSpecialty specialty) {
        specialtyMapper.insert(specialty);
        logger.info("添加特产成功，ID: {}, 名称: {}", specialty.getId(), specialty.getName());
    }

    public void deleteSpecialty(Long id) {
        specialtyMapper.delete(id);
        logger.info("删除特产成功，ID: {}", id);
    }

    public void updateSpecialty(LocalSpecialty specialty) {
        specialtyMapper.update(specialty);
        logger.info("更新特产成功，ID: {}", specialty.getId());
    }

    public List<LocalSpecialty> getSpecialtiesByCityId(Long cityId) {
        return specialtyMapper.selectByCityId(cityId);
    }

    public List<LocalSpecialty> getAllSpecialties(Integer page, Integer pageSize) {
        int offset = (page - 1) * pageSize;
        return specialtyMapper.selectAll(offset, pageSize);
    }

    public List<LocalSpecialty> searchSpecialties(String keyword) {
        return specialtyMapper.search(keyword);
    }

    public int getTotalCount() {
        return specialtyMapper.count();
    }

    public String generateMarketingText(String name, String features) {
        try {
            String systemPrompt = "你是一名资深的农产品带货主播和营销专家。你擅长用朴实真诚的语言打动人心，让顾客感受到农产品的新鲜和农户的不易。";

            String userPrompt = String.format(
                "请根据以下信息，写一段吸引人的朋友圈营销文案：\n\n" +
                "特产名称：%s\n" +
                "特点关键词：%s\n\n" +
                "要求：\n" +
                "1. 使用适当的 Emoji 表情符号，让文案更生动有趣。\n" +
                "2. 风格要亲切自然，像朋友推荐一样。\n" +
                "3. 突出【助农公益】主题，让大家知道购买是在帮助农户。\n" +
                "4. 强调产品的新鲜、绿色、原生态特点。\n" +
                "5. 文案长度控制在 100-150 字左右。\n" +
                "6. 以号召购买的温馨话语结尾。\n\n" +
                "请直接输出文案内容，不要有其他说明文字。",
                name, features
            );

            logger.info("调用 AI 生成营销文案，特产: {}, 特点: {}", name, features);

            String result = deepSeekService.callDeepSeekApi(systemPrompt, userPrompt);

            logger.info("成功生成营销文案，长度: {} 字符", result.length());

            return result;

        } catch (Exception e) {
            logger.error("生成营销文案失败: {}", e.getMessage(), e);
            return "抱歉，AI 文案生成服务暂时不可用，请稍后再试。";
        }
    }
}