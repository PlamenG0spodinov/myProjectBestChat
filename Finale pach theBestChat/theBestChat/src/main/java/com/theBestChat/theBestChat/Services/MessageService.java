package com.theBestChat.theBestChat.Services;

import com.theBestChat.theBestChat.Entityes.Channels;
import com.theBestChat.theBestChat.Entityes.Message;
import com.theBestChat.theBestChat.Entityes.Users;
import com.theBestChat.theBestChat.Repositoryes.ChannelRepo;
import com.theBestChat.theBestChat.Repositoryes.FriendsRepo;
import com.theBestChat.theBestChat.Repositoryes.MessageRepo;
import com.theBestChat.theBestChat.Repositoryes.UsersRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private  MessageRepo messageRepo;
    private  FriendsRepo friendsRepo;
    private  UsersRepo usersRepo;

    private ChannelRepo channelRepo;


    public MessageService(MessageRepo messageRepo, FriendsRepo friendsRepo, UsersRepo usersRepo, ChannelRepo channelRepo) {
        this.messageRepo = messageRepo;
        this.friendsRepo = friendsRepo;
        this.usersRepo = usersRepo;
        this.channelRepo = channelRepo;
    }

    public Message sendMessage(int senderId, int recipientId, String content) {
        Users sender = usersRepo.findById(senderId).orElseThrow();
        Users recipient = usersRepo.findById(recipientId).orElseThrow();

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

    public Message sendMessageToChannel(int senderId, int channelId, String content) {
        Users sender = usersRepo.findById(senderId).orElseThrow();
        Channels channel = channelRepo.findById(channelId).orElseThrow();

        Message message = new Message(sender, content, channel);
        return messageRepo.save(message);
    }
    public List<Message> getMessagesForChannel(int channelId) {
        return messageRepo.findMessagesByChannelId(channelId);
    }
}
