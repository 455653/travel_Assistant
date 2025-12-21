package com.learning.travelingassistant.service;

import com.learning.travelingassistant.dto.AttractionDetailDTO;
import com.learning.travelingassistant.entity.Attraction;

import java.util.List;

public interface AttractionService {
    List<Attraction> getTopAttractionsByCityId(Long cityId, Integer limit);
    
    AttractionDetailDTO getAttractionDetail(Long id);
}
