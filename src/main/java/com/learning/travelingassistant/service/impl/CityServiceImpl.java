package com.learning.travelingassistant.service.impl;

import com.learning.travelingassistant.entity.City;
import com.learning.travelingassistant.mapper.CityMapper;
import com.learning.travelingassistant.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityMapper cityMapper;

    @Override
    public List<City> getCitiesByProvinceId(Long provinceId) {
        return cityMapper.findByProvinceId(provinceId);
    }
}
