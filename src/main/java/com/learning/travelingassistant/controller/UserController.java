package com.learning.travelingassistant.controller;

import com.learning.travelingassistant.common.Result;
import com.learning.travelingassistant.entity.User;
import com.learning.travelingassistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * POST /api/user/login
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        // 参数校验
        if (username == null || username.trim().isEmpty()) {
            return Result.error("用户名不能为空");
        }
        if (password == null || password.trim().isEmpty()) {
            return Result.error("密码不能为空");
        }

        // 调用服务层登录
        User user = userService.login(username, password);

        if (user == null) {
            return Result.error("用户名或密码错误");
        }

        // 登录成功，返回用户信息（不包含密码）
        Map<String, Object> data = new HashMap<>();
        data.put("id", user.getId());
        data.put("username", user.getUsername());
        data.put("createTime", user.getCreateTime());

        return Result.success(data);
    }
}
