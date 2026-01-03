package com.learning.travelingassistant.service.impl;

import com.learning.travelingassistant.entity.FriendRequest;
import com.learning.travelingassistant.entity.Friendship;
import com.learning.travelingassistant.entity.User;
import com.learning.travelingassistant.mapper.FriendMapper;
import com.learning.travelingassistant.mapper.UserMapper;
import com.learning.travelingassistant.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FriendMapper friendMapper;

    @Override
    public User searchUserByAccountId(String accountId) {
        if (accountId == null || accountId.trim().isEmpty()) {
            throw new IllegalArgumentException("帐号ID不能为空");
        }
        return userMapper.findByAccountId(accountId);
    }

    @Override
    @Transactional
    public void sendRequest(Long senderId, String targetAccountId) {
        User sender = userMapper.findById(senderId);
        if (sender == null) {
            throw new IllegalArgumentException("发送者不存在");
        }

        User receiver = userMapper.findByAccountId(targetAccountId);
        if (receiver == null) {
            throw new IllegalArgumentException("目标用户不存在");
        }

        if (senderId.equals(receiver.getId())) {
            throw new IllegalArgumentException("不能添加自己为好友");
        }

        Friendship existingFriendship = friendMapper.findFriendship(senderId, receiver.getId());
        if (existingFriendship != null) {
            throw new IllegalArgumentException("你们已经是好友了");
        }

        FriendRequest existingRequest = friendMapper.findPendingRequest(senderId, receiver.getId());
        if (existingRequest != null) {
            throw new IllegalArgumentException("已发送过好友请求，请勿重复发送");
        }

        FriendRequest request = new FriendRequest();
        request.setSenderId(senderId);
        request.setReceiverId(receiver.getId());
        request.setStatus(0);
        friendMapper.insertRequest(request);
    }

    @Override
    @Transactional
    public void handleRequest(Long requestId, Integer status) {
        if (status == null || (status != 1 && status != 2)) {
            throw new IllegalArgumentException("状态参数错误");
        }

        FriendRequest request = friendMapper.findRequestById(requestId);
        if (request == null) {
            throw new IllegalArgumentException("请求不存在");
        }

        if (request.getStatus() != 0) {
            throw new IllegalArgumentException("该请求已被处理");
        }

        friendMapper.updateRequestStatus(requestId, status);

        if (status == 1) {
            Friendship friendship1 = new Friendship();
            friendship1.setUserId(request.getSenderId());
            friendship1.setFriendId(request.getReceiverId());
            friendMapper.insertFriendship(friendship1);

            Friendship friendship2 = new Friendship();
            friendship2.setUserId(request.getReceiverId());
            friendship2.setFriendId(request.getSenderId());
            friendMapper.insertFriendship(friendship2);
        }
    }

    @Override
    public List<Friendship> listFriends(Long userId) {
        return friendMapper.findFriendsByUserId(userId);
    }

    @Override
    public List<FriendRequest> listPendingRequests(Long userId) {
        return friendMapper.findPendingRequestsByReceiver(userId);
    }

    @Override
    @Transactional
    public void deleteFriend(Long userId, Long friendId) {
        Friendship friendship = friendMapper.findFriendship(userId, friendId);
        if (friendship == null) {
            throw new IllegalArgumentException("你们不是好友关系");
        }

        friendMapper.deleteFriendship(userId, friendId);
        friendMapper.deleteFriendship(friendId, userId);
    }
}