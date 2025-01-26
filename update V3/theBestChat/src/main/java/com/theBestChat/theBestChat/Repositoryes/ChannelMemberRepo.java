package com.theBestChat.theBestChat.Repositoryes;

import com.theBestChat.theBestChat.Entityes.ChannelMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChannelMemberRepo extends JpaRepository<ChannelMember, Integer> {

    List<ChannelMember> findByChannelId(int channelId);
}
