package com.learning.travelingassistant.service;

import com.learning.travelingassistant.entity.City;

import java.util.List;

public interface CityService {
    List<City> getCitiesByProvinceId(Long provinceId);
}
