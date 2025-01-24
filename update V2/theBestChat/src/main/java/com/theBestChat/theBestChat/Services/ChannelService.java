package com.theBestChat.theBestChat.Services;

import com.theBestChat.theBestChat.Entityes.Channels;
import com.theBestChat.theBestChat.Entityes.Users;
import com.theBestChat.theBestChat.Repositoryes.ChannelRepo;
import com.theBestChat.theBestChat.Repositoryes.UsersRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelService {

    private ChannelRepo channelRepo;
    private UsersRepo usersRepo;


    public ChannelService(ChannelRepo channelRepo, UsersRepo usersRepo){
        this.channelRepo = channelRepo;
        this.usersRepo = usersRepo;

    }

    public List<Channels> getChannelsByUser(Long userId){
        return this.channelRepo.findByOwnerId(userId);
    }

    public Channels createNewChannels(Channels channels){
        System.out.println("Saving channel: " + channels);

        return this.channelRepo.save(channels);
    }

    public boolean deleteChannel(int channelId, int userId) {
        var channel = channelRepo.findById(channelId).orElse(null);

        if (channel != null && channel.getOwner() != null && channel.getOwner().getId() == userId) {
            channel.setIsActive(0);
            channelRepo.save(channel);
            return true;
        }

        return false;
    }

    public boolean addUserToChannel(int channelId, int userId) {
        var channel = channelRepo.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("Channel not found"));

        var user = usersRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


        return true;
    }


}
