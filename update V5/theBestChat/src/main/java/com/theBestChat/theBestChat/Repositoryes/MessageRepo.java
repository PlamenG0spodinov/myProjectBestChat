package com.theBestChat.theBestChat.Repositoryes;

import com.theBestChat.theBestChat.Entityes.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE (m.sender.id = :userId1 AND m.recipient.id = :userId2) OR (m.sender.id = :userId2 AND m.recipient.id = :userId1) ORDER BY m.timestamp")
    List<Message> findMessagesBetweenUsers(int userId1, int userId2);

}
