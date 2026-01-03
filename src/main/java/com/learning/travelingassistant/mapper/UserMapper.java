package com.learning.travelingassistant.mapper;

import com.learning.travelingassistant.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    
    User findByUsername(@Param("username") String username);
    
    User findByPhone(@Param("phone") String phone);
    
    User findByIdCard(@Param("idCard") String idCard);
    
    User findByAccountId(@Param("accountId") String accountId);
    
    User findById(@Param("id") Long id);
    
    void insert(User user);
    
    void updateProfile(User user);
}
