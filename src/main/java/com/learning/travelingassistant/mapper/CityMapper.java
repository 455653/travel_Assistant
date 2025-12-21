package com.learning.travelingassistant.mapper;

import com.learning.travelingassistant.entity.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CityMapper {
    List<City> findByProvinceId(@Param("provinceId") Long provinceId);
    
    /**
     * 根据ID查询城市
     */
    City findById(@Param("id") Long id);
}
