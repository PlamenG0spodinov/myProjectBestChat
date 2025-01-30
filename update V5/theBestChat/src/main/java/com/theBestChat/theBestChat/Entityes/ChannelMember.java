package com.theBestChat.theBestChat.Entityes;

import jakarta.persistence.*;

@Entity
@Table(name = "channel_members")
public class ChannelMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channels channel;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "is_active")
    private int isActive = 1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Channels getChannel() {
        return channel;
    }

    public void setChannel(Channels channel) {
        this.channel = channel;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
}
