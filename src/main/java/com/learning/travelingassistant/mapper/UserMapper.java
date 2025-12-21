package com.learning.travelingassistant.mapper;

import com.learning.travelingassistant.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    
    /**
     * 根据用户名和密码查询用户
     */
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    
    /**
     * 根据用户名查询用户
     */
    User findByUsername(@Param("username") String username);
}
