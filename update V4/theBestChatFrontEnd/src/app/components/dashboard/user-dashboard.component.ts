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
  
    public channelId: string = '';
    public messageContent: string = '';
    public newChannelName: string = '';
    public newFriendUsername: string = '';
    public newFriendId: string = '';
    public otherChannels: ChannelMemberType[] = [];
    public messages: MessageType[] = [];
    public userChannels: ChannelType[] = [];
    public friends: FriendType[] = [];
    
    public userId: any;
    public selectedFriend: any;
    public selectedChannel: any;
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
      //this.loadOtherChannels();
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
        console.error('Channel name is empty!');
        return;
    }

    const newChannel: ChannelType = {
        name: this.newChannelName,
        owner: { id: +this.userId! }
    };

    console.log('Creating channel with data:', newChannel);

    this.channelApi.createChannel(newChannel).subscribe(
        () => {
            console.log('Channel created successfully!');
            this.newChannelName = ''; 
            this.loadUserChannels();
        },
        (error) => {
            console.error('Error creating channel:', error);
        }
    );
}

  private loadFriends() {
    this.friendApi.getFriends(this.userId!).subscribe(
      (friends) => {
      this.friends = friends
      console.log("Loaded friends:", this.friends);
    });
  }

  public addFriend() {
    if (!this.newFriendUsername.trim()) {
      return;
    }
  
    if (!this.userId) {
      console.error('User ID is missing!');
      return;
    }
  
  
    const userIdAsNumber = parseInt(this.userId, 10);
  
    this.friendApi.addFriendByUsername(userIdAsNumber, this.newFriendUsername).subscribe(
      () => {
        this.newFriendUsername = ''; 
        this.loadFriends(); 
      },
      (error: any) => {
        console.error('Error adding friend:', error);
      }
    );
  }

  // ne raboti 
  // private loadOtherChannels(){

  //   this.channelApi.getChannelsByInvitedUser(this.userId).subscribe(
  //     channels => {
  //       console.log(channels);
  //       this.otherChannels = channels; 
  //     });
  // }


  public showInviteForm(channel: any) {
    if (channel) {
    this.selectedChannel = channel;
    this.channelId = channel.id;
    console.log("Selected Channel:", this.selectedChannel);
    console.log("ChannelId:", this.channelId); 
    this.showInviteField = true;
   } else {
    console.log("No channel selected.");
  }
  }
  public inviteFriendToChannel(){
    console.log("Attempting to invite friend...");
    console.log("Selected Friend:", this.selectedFriend);  
    console.log("Selected Friend ID:", this.selectedFriend?.friendId);

    if (this.selectedFriend && this.selectedChannel) {
      console.log("Inviting friend with ID:", this.selectedFriend.friendId);

      this.channelApi.inviteFriendToChannel(this.selectedChannel.id, this.selectedFriend.friendId).subscribe(response => {

        console.log("Friend invited successfully:", response);
        
       
        //this.loadOtherChannels();
        this.addToOtherChannels(this.selectedChannel);
        this.showInviteField = false;
      });
    } 
  }
  
  public addToOtherChannels(channel: ChannelMemberType) {
    
    if (!this.otherChannels.includes(channel)) {
      this.otherChannels.push(channel);
      
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
          });
         
       });
    
    }
}