package com.theBestChat.theBestChat.Controllers;

import com.theBestChat.theBestChat.Entityes.Channels;

import com.theBestChat.theBestChat.Entityes.Friends;
import com.theBestChat.theBestChat.Entityes.Users;
import com.theBestChat.theBestChat.Http.AppResponse;
import com.theBestChat.theBestChat.Services.ChannelService;
import com.theBestChat.theBestChat.Services.UserService;
import com.theBestChat.theBestChat.Services.FriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class Controllerss {
    private UserService userService;
    private ChannelService channelService;
    private  FriendService friendService;


    public Controllerss(UserService userService, ChannelService channelService, FriendService friendService){
        this.userService = userService;
        this.channelService = channelService;
        this.friendService = friendService;

    }
    @GetMapping("/user")
    public ResponseEntity<?> takeAllUsers(){

        var collection = this.userService.getAllUsers();

        return AppResponse.success().withData(collection).withMessage("All users is hear").build();
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUsers(@RequestBody Users users){

        var response = this.userService.createNewUsers(users);

        return AppResponse.success().withData(response)
                .withMessage("Create new Users").build();
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Channels>> getUserChannels(@PathVariable Long userId) {
        List<Channels> channels = channelService.getChannelsByUser(userId);
        return ResponseEntity.ok(channels);
    }

    @PostMapping("/user/channels")
    public ResponseEntity<?> createChannels(@RequestBody Channels channels) {
        var response = this.channelService.createNewChannels(channels);

        return AppResponse.success().withData(response).withMessage("create successfully").build();
    }


        @DeleteMapping("/user/{userId}/channels/{channelId}")
    public ResponseEntity<?> deleteChannel(@PathVariable int channelId, @RequestParam int userId) {
        boolean isDeleted = channelService.deleteChannel(channelId, userId);

        if (isDeleted) {
            return ResponseEntity.ok("Channel deleted successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to delete channel. You might not be the owner.");
        }
    }


    @PostMapping("/friends/add/{userId}/{username}")
    public ResponseEntity<?> addFriendByUsername(@PathVariable int userId, @PathVariable String username) {
        try {
            Users friend = userService.findByUsername(username); // Намираме потребител по username
            if (friend == null) {
                return ResponseEntity.badRequest().body("User with username '" + username + "' not found.");
            }

            Friends newFriend = friendService.addFriend(userId, friend.getId()); // Добавяме приятел
            return ResponseEntity.ok(newFriend);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error adding friend: " + e.getMessage());
        }
    }

    @GetMapping("/friends/{userId}")
    public ResponseEntity<?> getFriends(@PathVariable int userId) {
        try {
            List<Map<String, String>> friends = friendService.getFriendsWithUsernames(userId);
            return ResponseEntity.ok(friends); // Връщаме списък с приятели (с username)
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving friends: " + e.getMessage());
        }
    }


    @PostMapping("/user/channels/add-user")
    public ResponseEntity<?> addUserToChannel(@RequestBody Map<String, Object> request) {
        int channelId = (int) request.get("channelId");
        int userId = (int) request.get("userId");

        var response = this.channelService.addUserToChannel(channelId, userId);

        return AppResponse.success()
                .withMessage("User added to channel successfully.")
                .withData(response)
                .build();
    }
}
