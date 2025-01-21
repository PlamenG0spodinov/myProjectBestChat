package com.theBestChat.theBestChat.Controllers;

import com.theBestChat.theBestChat.Entityes.Channels;

import com.theBestChat.theBestChat.Entityes.Users;
import com.theBestChat.theBestChat.Http.AppResponse;
import com.theBestChat.theBestChat.Services.ChannelService;
import com.theBestChat.theBestChat.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
public class Controllerss {
    private UserService userService;
    private ChannelService channelService;


    public Controllerss(UserService userService, ChannelService channelService){
        this.userService = userService;
        this.channelService = channelService;

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


    @GetMapping("/user/channels")
    public ResponseEntity<?> seeAllChannels(){

        var collection = this.channelService.getAllChannels();

        return AppResponse.success().withData(collection)
                .withMessage("all channel is here").build();
    }

    @PostMapping("/user/channels")
    public ResponseEntity<?> createChannels(@RequestBody Channels channels) {
        var response = this.channelService.createNewChannels(channels);

        return AppResponse.success().withData(response).withMessage("create successfully").build();
    }


        @DeleteMapping("/user/channels/{channelId}")
    public ResponseEntity<?> deleteChannel(@PathVariable int channelId, @RequestParam int userId) {
        boolean isDeleted = channelService.deleteChannel(channelId, userId);

        if (isDeleted) {
            return ResponseEntity.ok("Channel deleted successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to delete channel. You might not be the owner.");
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
