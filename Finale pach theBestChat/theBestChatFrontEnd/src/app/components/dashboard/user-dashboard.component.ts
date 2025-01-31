import { Component, inject } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { FormsModule } from '@angular/forms';
import { ChannelApi } from "../../service/channel.api";
import { FriendApi } from "../../service/friends.api";
import { ChannelType } from "../../models/channel.model";
import { FriendType } from "../../models/friend.model";
import { ChannelMemberType } from "../../models/channel-member.model";
import { MessageApi } from "../../service/message.api";
import { MessageType } from "../../models/message.model";


@Component({
    selector: 'app-user-dashboard',
    standalone: true,
    templateUrl: './user-dashboard.component.html',
    styleUrls: ['./user-dashboard.component.css'],
    imports: [FormsModule]
  })
  export class UserDashboardComponent {
  
    public channelId: number = 0;
    public messageContent: string = '';
    public newChannelName: string = '';
    public newFriendUsername: string = '';
    public newFriendId: string = '';
    public channelMessageContent: string = '';
    public otherChannels: ChannelMemberType[] = [];
    public messages: MessageType[] = [];
    public channelMessage: MessageType[] = [];
    public userChannels: ChannelType[] = [];
    public friends: FriendType[] = [];
    
    public userId: any;
    public selectedFriend: any;
    public selectedChannel: any;
    public showUpdateForm: boolean = false;
    public selectedChannelForUpdate: any;
    public showInviteField = false;
   
   


    private messageApi = inject(MessageApi);
    private friendApi = inject(FriendApi);
    private channelApi = inject(ChannelApi); 
    private route = inject(ActivatedRoute);

  ngOnInit() {
    this.userId = this.route.snapshot.paramMap.get('userId') || '';
    
    if (this.userId) {
      this.loadUserChannels();
      this.loadFriends();
      this.loadOtherChannels();
    }
  }

  private loadUserChannels() {
    this.channelApi.getUserChannels(this.userId!).subscribe(
      (result: ChannelType[]) => {
        this.userChannels = result; 
      });
  }

  public createChannel() {
    if (!this.newChannelName.trim()) {
        return;
    }

    const newChannel: ChannelType = {
        name: this.newChannelName,
        owner: { id: +this.userId! }
    };

    this.channelApi.createChannel(newChannel).subscribe(
        () => {

            this.newChannelName = ''; 
            this.loadUserChannels();
        });
}

deleteChannel(channel: any) {
  if (channel && channel.id) {
    this.channelApi.deleteChannel(channel.id).subscribe(
      (response) => {
        this.userChannels = this.userChannels.filter(c => c.id !== channel.id);
      });
  }
}


  public openUpdateForm(channel: any) {
    this.selectedChannelForUpdate = channel;
    this.newChannelName = channel.name; 
    this.showUpdateForm = true;
  }

  public closeUpdateForm() {
    this.showUpdateForm = false;
  }

  public saveUpdatedChannel() {
    if (this.selectedChannelForUpdate && this.newChannelName) {
      this.channelApi.updateChannel(this.selectedChannelForUpdate.id, this.newChannelName).subscribe(
        (response) => {
          this.selectedChannelForUpdate.name = this.newChannelName;
          this.showUpdateForm = false;
        });
    }
  }



  private loadFriends() {
    this.friendApi.getFriends(this.userId!).subscribe(
      (friends) => {
      this.friends = friends
    });
  }

  public addFriend() {
    if (!this.newFriendUsername.trim()) {
      return;
    }
  
    if (!this.userId) {
      return;
    }
  
  
    const userIdAsNumber = parseInt(this.userId, 10);
  
    this.friendApi.addFriendByUsername(userIdAsNumber, this.newFriendUsername).subscribe(
      () => {
        this.newFriendUsername = ''; 
        this.loadFriends(); 
      });
  }

  private loadOtherChannels() {
    this.channelApi.getChannelsByInvitedUser(this.userId!).subscribe(
      (channels: ChannelMemberType[]) => {
        this.otherChannels = channels;
      });
  }

  public showInviteForm(channel: any) {
    if (channel) {
    this.selectedChannel = channel;
    this.channelId = channel.id;
    this.showInviteField = true;
   }

  }
  public inviteFriendToChannel(){

    if (this.selectedFriend && this.selectedChannel) {
      const channelId = this.selectedChannel.id;

      this.channelApi.inviteFriendToChannel(this.selectedChannel.id, this.selectedFriend.friendId).subscribe(response => {

        this.loadOtherChannels();
        this.showInviteField = false;
      });
    } 
  }


  public openChat(friend: FriendType) {

    if (friend && friend.friendId) {
      this.selectedFriend = friend;
      this.loadMessages(friend.friendId);
    } 
  }
  
  public sendMessage() {

    if (this.selectedFriend.friendId && this.messageContent.trim() !== '') {

      this.messageApi.sendMessage(this.userId, this.selectedFriend.friendId, this.messageContent)
      .subscribe((message) => {
        this.messages.push(message); 
        this.messageContent = '';
    
      });
    } 
  
  }

    public loadMessages(friendId: number) {
    
      this.messageApi.getMessages(this.userId, friendId).subscribe((response) => {  
        this.messages = response.data.map((message: MessageType) => {
          return message.content;
        } );
      });
    }

    public openChannelChat(channel: any) {
      this.selectedChannel = channel; 
      this.loadMessagesForChannel(channel.id); 
    }
  
   
    public loadMessagesForChannel(channelId: number) {
      this.messageApi.getChannelMessages(channelId).subscribe((response) => {
        this.messages = response.data.map((channelMessage: MessageType) => {
          return channelMessage.content;
        } );
      });
    }
  
  
    public sendChannelMessage() {
     
        this.messageApi.sendMessageToChannel(this.userId, this.channelId, this.channelMessageContent)
        .subscribe((channelMessage) => {
          this.messages.push(channelMessage); 
          this.channelMessageContent = '';
      
        });
        
      
    }
}