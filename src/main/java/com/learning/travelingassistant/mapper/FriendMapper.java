package com.learning.travelingassistant.mapper;

import com.learning.travelingassistant.entity.FriendRequest;
import com.learning.travelingassistant.entity.Friendship;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FriendMapper {

    void insertRequest(FriendRequest request);

    void updateRequestStatus(@Param("id") Long id, @Param("status") Integer status);

    FriendRequest findRequestById(@Param("id") Long id);

    FriendRequest findPendingRequest(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

    List<FriendRequest> findPendingRequestsByReceiver(@Param("receiverId") Long receiverId);

    void insertFriendship(Friendship friendship);

    void deleteFriendship(@Param("userId") Long userId, @Param("friendId") Long friendId);

    Friendship findFriendship(@Param("userId") Long userId, @Param("friendId") Long friendId);

    List<Friendship> findFriendsByUserId(@Param("userId") Long userId);
}