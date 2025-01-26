package com.theBestChat.theBestChat.Controllers;

import com.theBestChat.theBestChat.Entityes.Channels;

import com.theBestChat.theBestChat.Entityes.Friends;
import com.theBestChat.theBestChat.Entityes.Users;
import com.theBestChat.theBestChat.Http.AppResponse;
import com.theBestChat.theBestChat.Repositoryes.ChannelRepo;
import com.theBestChat.theBestChat.Repositoryes.UsersRepo;
import com.theBestChat.theBestChat.Services.ChannelService;
import com.theBestChat.theBestChat.Services.UserService;
import com.theBestChat.theBestChat.Services.FriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.channels.Channel;
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
    public ResponseEntity<?> deleteChannel(@PathVariable int channelId, @PathVariable int userId) {
        boolean isUpdateSuccessful = channelService.deleteChannel(channelId, userId);

        if (!isUpdateSuccessful) {
            return AppResponse.error()
                    .withMessage("Failed to delete channel. You might not be the owner.")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Channel deleted successfully.")
                .build();
    }


    @PostMapping("/friends/add/{userId}/{username}")
    public ResponseEntity<?> addFriendByUsername(@PathVariable int userId, @PathVariable String username) {
        try {
            Users friend = userService.findByUsername(username);
            if (friend == null) {
                return ResponseEntity.badRequest().body("User with username '" + username + "' not found.");
            }

            Friends newFriend = friendService.addFriend(userId, friend.getId());
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

    @PostMapping("/channels/{channelId}/invite/{friendId}")
    public ResponseEntity<?> inviteFriendToChannel(@PathVariable int friendId, @PathVariable int channelId) {
        try {
            boolean success = channelService.inviteFriendToChannel(friendId, channelId);
            if (!success) {
                return AppResponse.error().withMessage("Failed to invite friend.").build();
            }
            return AppResponse.success().withMessage("Friend successfully invited to the channel.").build();
        } catch (Exception e) {
            return AppResponse.error().withMessage("Error inviting friend: " + e.getMessage()).build();
        }
    }



}
