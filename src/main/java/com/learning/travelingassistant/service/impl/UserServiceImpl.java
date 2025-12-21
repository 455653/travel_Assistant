package com.learning.travelingassistant.service.impl;

import com.learning.travelingassistant.entity.User;
import com.learning.travelingassistant.mapper.UserMapper;
import com.learning.travelingassistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        return userMapper.findByUsernameAndPassword(username, password);
    }
}
