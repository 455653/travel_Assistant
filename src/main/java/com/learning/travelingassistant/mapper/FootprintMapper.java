package com.learning.travelingassistant.mapper;

import com.learning.travelingassistant.entity.FootprintCollaborator;
import com.learning.travelingassistant.entity.FootprintPhoto;
import com.learning.travelingassistant.entity.TravelFootprint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FootprintMapper {
    
    void insertFootprint(TravelFootprint footprint);
    
    void insertCollaborator(FootprintCollaborator collaborator);
    
    void insertPhoto(FootprintPhoto photo);
    
    TravelFootprint findById(@Param("id") Long id);
    
    List<TravelFootprint> findByProvinceAndUser(@Param("provinceId") Long provinceId, @Param("userId") Long userId);
    
    List<TravelFootprint> findAllByUser(@Param("userId") Long userId);
    
    FootprintCollaborator findCollaborator(@Param("footprintId") Long footprintId, @Param("userId") Long userId);
    
    List<FootprintPhoto> findPhotosByFootprint(@Param("footprintId") Long footprintId);
    
    FootprintPhoto findPhotoById(@Param("id") Long id);
    
    void deletePhoto(@Param("id") Long id);
    
    List<FootprintCollaborator> findCollaboratorsByFootprint(@Param("footprintId") Long footprintId);
}