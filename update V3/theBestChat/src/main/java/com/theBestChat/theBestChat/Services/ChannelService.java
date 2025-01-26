package com.theBestChat.theBestChat.Services;

import com.theBestChat.theBestChat.Entityes.ChannelMember;
import com.theBestChat.theBestChat.Entityes.Channels;
import com.theBestChat.theBestChat.Entityes.Friends;
import com.theBestChat.theBestChat.Entityes.Users;
import com.theBestChat.theBestChat.Repositoryes.ChannelMemberRepo;
import com.theBestChat.theBestChat.Repositoryes.ChannelRepo;
import com.theBestChat.theBestChat.Repositoryes.FriendsRepo;
import org.springframework.stereotype.Service;

import java.nio.channels.Channel;
import java.util.List;

@Service
public class ChannelService {

    private ChannelRepo channelRepo;
    private FriendService friendService;
    private ChannelMemberRepo channelMemberRepo;


    public ChannelService(ChannelRepo channelRepo, FriendService friendService, ChannelMemberRepo channelMemberRepo){
        this.channelRepo = channelRepo;
        this.friendService = friendService;
        this.channelMemberRepo = channelMemberRepo;

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

    public boolean inviteFriendToChannel(int friendId, int channelId) {

    Channels channel = channelRepo.findById(channelId).orElse(null);
    if (channel == null) {
        throw new IllegalArgumentException("Channel does not exist.");
    }

    ChannelMember newMember = new ChannelMember();
    newMember.setChannel(channel);
    newMember.setUser(new Users(friendId));
    channelMemberRepo.save(newMember);

    return true;
    }

}
