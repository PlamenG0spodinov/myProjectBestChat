package com.theBestChat.theBestChat.Repositoryes;

import com.theBestChat.theBestChat.Entityes.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendsRepo extends JpaRepository<Friends, Integer> {

    @Query("SELECT f FROM Friends f WHERE f.userId = :userId AND f.isActive = 1")
    List<Friends> findByUserId(@Param("userId") int userId);

    @Query("SELECT f FROM Friends f WHERE f.userId = :userId AND f.friendId = :friendId AND f.isActive = 1")
    Friends findFriendship(@Param("userId") int userId, @Param("friendId") int friendId);

}