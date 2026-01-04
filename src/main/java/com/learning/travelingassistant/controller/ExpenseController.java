package com.learning.travelingassistant.controller;
import com.learning.travelingassistant.common.Result; import com.learning.travelingassistant.entity.ExpenseRecord; import com.learning.travelingassistant.service.ExpenseService; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal; import java.time.LocalDate; import java.util.List; import java.util.Map;
@RestController @RequestMapping("/api/expense") public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/list")
    public Result<List<ExpenseRecord>> listExpenses(
            @RequestParam Long footprintId,
            @RequestParam Long userId) {
        try {
            List<ExpenseRecord> expenses = expenseService.listExpenses(userId, footprintId);
            return Result.success(expenses);
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("获取账单列表失败: " + e.getMessage());
        }
    }

    @GetMapping("/stats")
    public Result<Map<String, Object>> getStatistics(
            @RequestParam Long footprintId,
            @RequestParam Long userId) {
        try {
            Map<String, Object> stats = expenseService.getStatistics(userId, footprintId);
            return Result.success(stats);
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("获取统计数据失败: " + e.getMessage());
        }
    }

    @PostMapping("/ai/parse")
    public Result<Map<String, Object>> parseByAI(@RequestBody Map<String, String> params) {
        try {
            String text = params.get("text");
            if (text == null || text.trim().isEmpty()) {
                return Result.error("输入文本不能为空");
            }

            Map<String, Object> result = expenseService.parseExpenseByAI(text);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("AI 识别失败，请手动输入: " + e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result<String> addExpense(@RequestBody Map<String, Object> params) {
        try {
            Long userId = Long.parseLong(params.get("userId").toString());
            Long footprintId = Long.parseLong(params.get("footprintId").toString());

            ExpenseRecord record = new ExpenseRecord();
            record.setFootprintId(footprintId);
            record.setCategory(params.get("category").toString());
            record.setAmount(new BigDecimal(params.get("amount").toString()));
            record.setExpenseDate(LocalDate.parse(params.get("expenseDate").toString()));
            record.setDescription(params.get("description") != null ? params.get("description").toString() : "");

            expenseService.addExpense(userId, record);
            return Result.success("记账成功");
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("记账失败: " + e.getMessage());
        }
    }

    @PostMapping("/delete")
    public Result<String> deleteExpense(@RequestParam Long id, @RequestParam Long userId) {
        try {
            expenseService.deleteExpense(userId, id);
            return Result.success("删除成功");
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("删除失败: " + e.getMessage());
        }
    }
}