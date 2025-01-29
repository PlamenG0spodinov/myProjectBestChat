package com.theBestChat.theBestChat.Controllers;

import com.theBestChat.theBestChat.Entityes.*;

import com.theBestChat.theBestChat.Http.AppResponse;
import com.theBestChat.theBestChat.Repositoryes.ChannelRepo;
import com.theBestChat.theBestChat.Repositoryes.UsersRepo;
import com.theBestChat.theBestChat.Services.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Controller
public class Controllerss {
    private UserService userService;
    private ChannelService channelService;
    private  FriendService friendService;
    private  ChannelMemberService channelMemberService;
    private MessageService messageService;


    public Controllerss(UserService userService, ChannelService channelService, FriendService friendService, ChannelMemberService channelMemberService, MessageService messageService){
        this.userService = userService;
        this.channelService = channelService;
        this.friendService = friendService;
        this.channelMemberService = channelMemberService;
        this.messageService = messageService;
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
    @GetMapping("/user/{userId}/invited/channels/{channelId}")
    public ResponseEntity<List<ChannelMember>> getChannelsByInvitedUser(@PathVariable int userId, @PathVariable int channelId) {
        List<ChannelMember> channelsMember = channelMemberService.getChannelsByInvitedUser(userId, channelId);
        return ResponseEntity.ok(channelsMember);
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

    @PostMapping("/message/send/{senderId}/{recipientId}")
    public ResponseEntity<?> sendMessage(@PathVariable int senderId, @PathVariable int recipientId, @RequestBody String content) {
            Message message = messageService.sendMessage(senderId, recipientId, content);
            return AppResponse.success().withData(message).withMessage("Message sent successfully").build();

    }

    @GetMapping("/message/chat/{userId1}/{userId2}")
    public ResponseEntity<?> getMessages(@PathVariable int userId1, @PathVariable int userId2) {
        List<Message> messages = messageService.getMessages(userId1, userId2);
            return AppResponse.success().withData(messages).withMessage("Messages retrieved successfully").build();

    }
}
