package com.theBestChat.theBestChat.Services;

import com.theBestChat.theBestChat.Entityes.ChannelMember;
import com.theBestChat.theBestChat.Entityes.Channels;
import com.theBestChat.theBestChat.Repositoryes.ChannelMemberRepo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ChannelMemberService {

    private ChannelMemberRepo channelMemberRepo;
    public ChannelMemberService (ChannelMemberRepo channelMemberRepo){
       this.channelMemberRepo = channelMemberRepo;

    }
    public List<ChannelMember> getChannelsByInvitedUser(Long userId){
        return this.channelMemberRepo.findByUserIdAndIsActive(userId, 1);
    }

}
