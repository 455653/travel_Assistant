package com.learning.travelingassistant.mapper;

import com.learning.travelingassistant.entity.LocalSpecialty;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LocalSpecialtyMapper {

    void insert(LocalSpecialty specialty);

    void delete(@Param("id") Long id);

    void update(LocalSpecialty specialty);

    List<LocalSpecialty> selectByCityId(@Param("cityId") Long cityId);

    List<LocalSpecialty> selectAll(@Param("offset") Integer offset, @Param("limit") Integer limit);

    List<LocalSpecialty> search(@Param("keyword") String keyword);

    int count();
}