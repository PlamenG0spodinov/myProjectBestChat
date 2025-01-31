package com.theBestChat.theBestChat.Repositoryes;

import com.theBestChat.theBestChat.Entityes.Channels;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface ChannelRepo extends JpaRepository<Channels, Integer> {

    List<Channels> findByOwnerIdAndIsActive(Long ownerId, int isActive);


}
