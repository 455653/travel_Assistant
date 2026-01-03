package com.learning.travelingassistant.controller;

import com.learning.travelingassistant.common.Result;
import com.learning.travelingassistant.entity.FriendRequest;
import com.learning.travelingassistant.entity.Friendship;
import com.learning.travelingassistant.entity.User;
import com.learning.travelingassistant.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @GetMapping("/search")
    public Result<Map<String, Object>> searchUser(@RequestParam String accountId) {
        try {
            User user = friendService.searchUserByAccountId(accountId);
            if (user == null) {
                return Result.error("未找到该用户");
            }

            Map<String, Object> data = new HashMap<>();
            data.put("id", user.getId());
            data.put("username", user.getUsername());
            data.put("accountId", user.getAccountId());

            return Result.success(data);
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("搜索失败");
        }
    }

    @PostMapping("/request/send")
    public Result<String> sendRequest(@RequestBody Map<String, Object> params) {
        try {
            Long senderId = Long.parseLong(params.get("senderId").toString());
            String targetAccountId = params.get("targetAccountId").toString();

            friendService.sendRequest(senderId, targetAccountId);
            return Result.success("好友请求已发送");
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("发送请求失败");
        }
    }

    @PostMapping("/request/handle")
    public Result<String> handleRequest(@RequestBody Map<String, Object> params) {
        try {
            Long requestId = Long.parseLong(params.get("requestId").toString());
            Integer status = Integer.parseInt(params.get("status").toString());

            friendService.handleRequest(requestId, status);
            return Result.success(status == 1 ? "已同意好友请求" : "已拒绝好友请求");
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("处理请求失败");
        }
    }

    @GetMapping("/list")
    public Result<List<Friendship>> listFriends(@RequestParam Long userId) {
        try {
            List<Friendship> friends = friendService.listFriends(userId);
            return Result.success(friends);
        } catch (Exception e) {
            return Result.error("获取好友列表失败");
        }
    }

    @GetMapping("/requests/pending")
    public Result<List<FriendRequest>> listPendingRequests(@RequestParam Long userId) {
        try {
            List<FriendRequest> requests = friendService.listPendingRequests(userId);
            return Result.success(requests);
        } catch (Exception e) {
            return Result.error("获取好友请求失败");
        }
    }

    @PostMapping("/delete")
    public Result<String> deleteFriend(@RequestBody Map<String, Object> params) {
        try {
            Long userId = Long.parseLong(params.get("userId").toString());
            Long friendId = Long.parseLong(params.get("friendId").toString());

            friendService.deleteFriend(userId, friendId);
            return Result.success("已删除好友");
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("删除好友失败");
        }
    }
}