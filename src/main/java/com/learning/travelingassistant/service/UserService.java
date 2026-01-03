package com.learning.travelingassistant.service;

import com.learning.travelingassistant.entity.User;

public interface UserService {
    
    User login(String username, String password);
    
    void register(User user);
}
