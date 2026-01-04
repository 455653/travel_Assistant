package com.learning.travelingassistant.service.impl;
import com.fasterxml.jackson.databind.JsonNode; import com.fasterxml.jackson.databind.ObjectMapper; import com.learning.travelingassistant.entity.ExpenseRecord; import com.learning.travelingassistant.entity.FootprintCollaborator; import com.learning.travelingassistant.entity.TravelFootprint; import com.learning.travelingassistant.mapper.ExpenseMapper; import com.learning.travelingassistant.mapper.FootprintMapper; import com.learning.travelingassistant.service.DeepSeekService; import com.learning.travelingassistant.service.ExpenseService; import org.slf4j.Logger; import org.slf4j.LoggerFactory; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal; import java.time.LocalDate; import java.util.HashMap; import java.util.List; import java.util.Map;
@Service public class ExpenseServiceImpl implements ExpenseService {
    private static final Logger logger = LoggerFactory.getLogger(ExpenseServiceImpl.class);

    @Autowired
    private ExpenseMapper expenseMapper;

    @Autowired
    private FootprintMapper footprintMapper;

    @Autowired
    private DeepSeekService deepSeekService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private void checkPermission(Long userId, Long footprintId) {
        FootprintCollaborator collaborator = footprintMapper.findCollaborator(footprintId, userId);
        if (collaborator == null) {
            throw new IllegalArgumentException("您没有权限操作此账本");
        }
    }

    @Override
    @Transactional
    public void addExpense(Long userId, ExpenseRecord record) {
        checkPermission(userId, record.getFootprintId());

        if (record.getAmount() == null || record.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("金额必须大于0");
        }
        if (record.getCategory() == null || record.getCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("分类不能为空");
        }
        if (record.getExpenseDate() == null) {
            throw new IllegalArgumentException("消费日期不能为空");
        }

        record.setPayerId(userId);
        expenseMapper.insert(record);
        logger.info("用户 {} 在足迹 {} 添加了记账记录: {} 元", userId, record.getFootprintId(), record.getAmount());
    }

    @Override
    @Transactional
    public void deleteExpense(Long userId, Long expenseId) {
        ExpenseRecord record = expenseMapper.selectById(expenseId);
        if (record == null) {
            throw new IllegalArgumentException("记账记录不存在");
        }

        checkPermission(userId, record.getFootprintId());

        TravelFootprint footprint = footprintMapper.findById(record.getFootprintId());
        if (!record.getPayerId().equals(userId) && !footprint.getCreatorId().equals(userId)) {
            throw new IllegalArgumentException("只能删除自己添加的记录或作为创建者删除");
        }

        expenseMapper.deleteById(expenseId);
        logger.info("用户 {} 删除了记账记录 {}", userId, expenseId);
    }

    @Override
    @Transactional
    public void updateExpense(Long userId, ExpenseRecord record) {
        ExpenseRecord existing = expenseMapper.selectById(record.getId());
        if (existing == null) {
            throw new IllegalArgumentException("记账记录不存在");
        }

        checkPermission(userId, existing.getFootprintId());

        if (!existing.getPayerId().equals(userId)) {
            throw new IllegalArgumentException("只能修改自己添加的记录");
        }

        expenseMapper.update(record);
        logger.info("用户 {} 修改了记账记录 {}", userId, record.getId());
    }

    @Override
    public List<ExpenseRecord> listExpenses(Long userId, Long footprintId) {
        checkPermission(userId, footprintId);
        return expenseMapper.selectByFootprintId(footprintId);
    }

    @Override
    public Map<String, Object> getStatistics(Long userId, Long footprintId) {
        checkPermission(userId, footprintId);

        List<Map<String, Object>> stats = expenseMapper.selectStatistics(footprintId);

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Map<String, Object> stat : stats) {
            BigDecimal amount = (BigDecimal) stat.get("totalAmount");
            if (amount != null) {
                totalAmount = totalAmount.add(amount);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("categoryStats", stats);
        result.put("totalAmount", totalAmount);

        return result;
    }

    @Override
    public Map<String, Object> parseExpenseByAI(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("输入文本不能为空");
        }

        try {
            String jsonResponse = deepSeekService.parseExpenseText(text);
            if (jsonResponse == null) {
                throw new RuntimeException("AI 解析失败");
            }

            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            Map<String, Object> result = new HashMap<>();
            result.put("amount", jsonNode.has("amount") ? jsonNode.get("amount").asDouble() : 0);
            result.put("category", jsonNode.has("category") ? jsonNode.get("category").asText() : "其他");
            result.put("date", jsonNode.has("date") ? jsonNode.get("date").asText() : LocalDate.now().toString());
            result.put("description", jsonNode.has("description") ? jsonNode.get("description").asText() : "");

            logger.info("AI 成功解析消费记录: {}", result);
            return result;

        } catch (Exception e) {
            logger.error("AI 解析消费记录失败: {}", e.getMessage(), e);
            throw new RuntimeException("AI 识别失败，请手动输入");
        }
    }
}