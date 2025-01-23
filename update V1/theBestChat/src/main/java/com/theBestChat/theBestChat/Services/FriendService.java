package com.theBestChat.theBestChat.Services;

import com.theBestChat.theBestChat.Entityes.Friends;
import com.theBestChat.theBestChat.Entityes.Users;
import com.theBestChat.theBestChat.Repositoryes.FriendsRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FriendService {

    private FriendsRepo friendsRepo;
    private UserService userService;

    public FriendService(FriendsRepo friendsRepo, UserService userService){
        this.friendsRepo = friendsRepo;
        this.userService = userService;
    }


    public Friends addFriend(int userId, int friendId) {
        if (userId == friendId) {
            throw new IllegalArgumentException("Cannot add yourself as a friend.");
        }

        if (friendsRepo.findFriendship(userId, friendId) != null) {
            throw new IllegalArgumentException("Users are already friends.");
        }

        Friends friendship = new Friends();
        friendship.setUserId(userId);
        friendship.setFriendId(friendId);
        return friendsRepo.save(friendship);
    }
    public List<Map<String, String>> getFriendsWithUsernames(int userId) {
        List<Friends> friends = friendsRepo.findByUserId(userId);
        List<Map<String, String>> friendDetails = new ArrayList<>();

        for (Friends friend : friends) {
            Users user = userService.getAllUsers()
                    .stream()
                    .filter(u -> u.getId() == friend.getFriendId())
                    .findFirst()
                    .orElse(null);

            if (user != null) {
                Map<String, String> friendData = new HashMap<>();
                friendData.put("friendId", String.valueOf(friend.getFriendId()));
                friendData.put("username", user.getUsername());
                friendDetails.add(friendData);
            }
        }

        return friendDetails;
    }
}
