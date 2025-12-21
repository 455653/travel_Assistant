package com.learning.travelingassistant.service.impl;

import com.learning.travelingassistant.entity.Province;
import com.learning.travelingassistant.mapper.ProvinceMapper;
import com.learning.travelingassistant.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    private ProvinceMapper provinceMapper;

    @Override
    public List<Province> getAllProvinces() {
        return provinceMapper.findAll();
    }
}
