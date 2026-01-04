package com.learning.travelingassistant.mapper;

import com.learning.travelingassistant.entity.ExpenseRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ExpenseMapper {

    void insert(ExpenseRecord record);

    void deleteById(@Param("id") Long id);

    void update(ExpenseRecord record);

    List<ExpenseRecord> selectByFootprintId(@Param("footprintId") Long footprintId);

    List<Map<String, Object>> selectStatistics(@Param("footprintId") Long footprintId);

    ExpenseRecord selectById(@Param("id") Long id);
}