package com.learning.travelingassistant.service;

import com.learning.travelingassistant.entity.User;

public interface UserService {
    
    /**
     * 用户登录
     */
    User login(String username, String password);
}
