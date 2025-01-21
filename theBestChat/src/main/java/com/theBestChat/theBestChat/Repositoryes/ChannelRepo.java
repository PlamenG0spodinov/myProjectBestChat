package com.theBestChat.theBestChat.Repositoryes;

import com.theBestChat.theBestChat.Entityes.Channels;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
public interface ChannelRepo extends JpaRepository<Channels, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE Channels c SET c.isActive = :isActive WHERE c.id = :id")
    boolean updateIsActiveById(int id, int isActive);
}
