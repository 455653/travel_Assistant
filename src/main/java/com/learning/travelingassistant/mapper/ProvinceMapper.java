package com.learning.travelingassistant.mapper;

import com.learning.travelingassistant.entity.Province;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProvinceMapper {
    List<Province> findAll();
}
