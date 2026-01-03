package com.learning.travelingassistant.service;

import com.learning.travelingassistant.dto.FootprintDTO;
import com.learning.travelingassistant.entity.FootprintCollaborator;
import com.learning.travelingassistant.entity.FootprintPhoto;
import com.learning.travelingassistant.entity.TravelFootprint;

import java.util.List;
import java.util.Map;

public interface FootprintService {
    
    void createFootprint(Long creatorId, FootprintDTO dto);
    
    List<TravelFootprint> listFootprints(Long provinceId, Long userId);
    
    List<TravelFootprint> listAllFootprints(Long userId);
    
    Map<String, Object> getFootprintDetail(Long footprintId, Long userId);
    
    void addPhoto(Long userId, Long footprintId, String imageUrl);
    
    void deletePhoto(Long userId, Long photoId);
    
    void deleteFootprint(Long userId, Long footprintId);
    
    void updateTitle(Long userId, Long footprintId, String title);
    
    void addNote(Long userId, Long footprintId, String content);
}