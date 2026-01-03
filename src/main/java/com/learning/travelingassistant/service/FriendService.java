package com.learning.travelingassistant.service;

import com.learning.travelingassistant.entity.FriendRequest;
import com.learning.travelingassistant.entity.Friendship;
import com.learning.travelingassistant.entity.User;

import java.util.List;

public interface FriendService {

    User searchUserByAccountId(String accountId);

    void sendRequest(Long senderId, String targetAccountId);

    void handleRequest(Long requestId, Integer status);

    List<Friendship> listFriends(Long userId);

    List<FriendRequest> listPendingRequests(Long userId);

    void deleteFriend(Long userId, Long friendId);
}