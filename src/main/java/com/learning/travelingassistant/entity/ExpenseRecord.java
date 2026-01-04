package com.learning.travelingassistant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRecord {
    private Long id;
    private Long footprintId;
    private Long payerId;
    private String category;
    private BigDecimal amount;
    private LocalDate expenseDate;
    private String description;
    private LocalDateTime createTime;

    private String payerName;
}