package com.learning.travelingassistant.service.impl;

import com.learning.travelingassistant.entity.User;
import com.learning.travelingassistant.mapper.UserMapper;
import com.learning.travelingassistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        String encryptedPassword = md5(password);
        return userMapper.findByUsernameAndPassword(username, encryptedPassword);
    }

    @Override
    public void register(User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("密码不能为空");
        }
        if (user.getPhone() == null || user.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("手机号不能为空");
        }

        User existingUser = userMapper.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new IllegalArgumentException("用户名已存在");
        }

        User existingPhone = userMapper.findByPhone(user.getPhone());
        if (existingPhone != null) {
            throw new IllegalArgumentException("手机号已被注册");
        }

        if (user.getIdCard() != null && !user.getIdCard().trim().isEmpty()) {
            User existingIdCard = userMapper.findByIdCard(user.getIdCard());
            if (existingIdCard != null) {
                throw new IllegalArgumentException("身份证号已被注册");
            }
        }

        String encryptedPassword = md5(user.getPassword());
        user.setPassword(encryptedPassword);

        userMapper.insert(user);
    }

    private String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5加密失败", e);
        }
    }
}
