package com.theBestChat.theBestChat.Services;

import com.theBestChat.theBestChat.Entityes.ChannelMember;
import com.theBestChat.theBestChat.Entityes.Channels;
import com.theBestChat.theBestChat.Entityes.Users;
import com.theBestChat.theBestChat.Repositoryes.ChannelMemberRepo;
import com.theBestChat.theBestChat.Repositoryes.ChannelRepo;
import com.theBestChat.theBestChat.Repositoryes.UsersRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelService {

    private ChannelRepo channelRepo;
    private FriendService friendService;
    private UsersRepo usersRepo;
    private ChannelMemberRepo channelMemberRepo;


    public ChannelService(ChannelRepo channelRepo, FriendService friendService, UsersRepo usersRepo, ChannelMemberRepo channelMemberRepo){
        this.channelRepo = channelRepo;
        this.friendService = friendService;
        this.usersRepo = usersRepo;
        this.channelMemberRepo = channelMemberRepo;

    }

    public List<Channels> getChannelsByUser(Long userId){
        return this.channelRepo.findByOwnerIdAndIsActive(userId, 1);
    }
    public Channels createNewChannels(Channels channels){

        return this.channelRepo.save(channels);
    }
    public boolean updateChannels(int channelId, String name) {

        Channels channel = channelRepo.findById(channelId).orElse(null);
        if (channel == null || channel.getIsActive() == 0) {
            return false;
        }

        if (name != null && !name.isEmpty() && !channel.getName().equals(name)) {
            channel.setName(name);
            channelRepo.save(channel);
        }

        return true;
    }

    public boolean deleteChannel(int channelId) {
        var channel = channelRepo.findById(channelId).orElse(null);
        if (channel == null || channel.getIsActive() == 0) {
            return false;
        }

        channel.setIsActive(0);
        channelRepo.save(channel);
        return true;
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
