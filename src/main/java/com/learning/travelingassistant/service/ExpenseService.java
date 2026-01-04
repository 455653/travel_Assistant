package com.learning.travelingassistant.service;
import com.learning.travelingassistant.entity.ExpenseRecord;
import java.util.List; import java.util.Map;
public interface ExpenseService {
    void addExpense(Long userId, ExpenseRecord record);

    void deleteExpense(Long userId, Long expenseId);

    void updateExpense(Long userId, ExpenseRecord record);

    List<ExpenseRecord> listExpenses(Long userId, Long footprintId);

    Map<String, Object> getStatistics(Long userId, Long footprintId);

    Map<String, Object> parseExpenseByAI(String text);
}