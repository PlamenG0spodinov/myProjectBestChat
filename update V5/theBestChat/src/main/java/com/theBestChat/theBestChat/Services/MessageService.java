package com.theBestChat.theBestChat.Services;

import com.theBestChat.theBestChat.Entityes.Friends;
import com.theBestChat.theBestChat.Entityes.Message;
import com.theBestChat.theBestChat.Entityes.Users;
import com.theBestChat.theBestChat.Repositoryes.FriendsRepo;
import com.theBestChat.theBestChat.Repositoryes.MessageRepo;
import com.theBestChat.theBestChat.Repositoryes.UsersRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepo messageRepo;
    private final FriendsRepo friendsRepo;
    private final UsersRepo usersRepo;

    public MessageService(MessageRepo messageRepo, FriendsRepo friendsRepo, UsersRepo usersRepo) {
        this.messageRepo = messageRepo;
        this.friendsRepo = friendsRepo;
        this.usersRepo = usersRepo;
    }

    public Message sendMessage(int senderId, int recipientId, String content) {
        Users sender = usersRepo.findById(senderId).orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        Users recipient = usersRepo.findById(recipientId).orElseThrow(() -> new IllegalArgumentException("Recipient not found"));

        if (friendsRepo.findFriendship(senderId, recipientId) == null) {
            throw new IllegalArgumentException("Users are not friends.");
        }

        Message message = new Message(sender, recipient, content);
        return messageRepo.save(message);
    }

    public List<Message> getMessages(int userId1, int userId2) {
        Users user1 = usersRepo.findById(userId1).orElseThrow() ;
        Users user2 = usersRepo.findById(userId2).orElseThrow();

        if (friendsRepo.findFriendship(userId1, userId2) == null) {
            throw new IllegalArgumentException("Users are not friends.");
        }

        return messageRepo.findMessagesBetweenUsers(userId1, userId2);
    }
}
