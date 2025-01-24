import { Component, inject } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { FormsModule } from '@angular/forms';
import { ChannelApi } from "../../service/channel.api";
import { FriendApi } from "../../service/friends.api";
import { ChannelType } from "../../models/channel.model";


@Component({
    selector: 'app-user-dashboard',
    standalone: true,
    templateUrl: './user-dashboard.component.html',
    styleUrls: ['./user-dashboard.component.css'],
    imports: [FormsModule]
  })
  export class UserDashboardComponent {
  
    public userId: string | null = null;
    public userChannels: ChannelType[] = [];
    public newChannelName: string = '';
    public newFriendUsername: string = '';

    public friends: any[] = [];
    public newFriendId: string = '';

    private friendApi = inject(FriendApi);
    private channelApi = inject(ChannelApi); 
    private route = inject(ActivatedRoute);

  ngOnInit() {
    this.userId = this.route.snapshot.paramMap.get('userId');
    if (this.userId) {
      this.loadUserChannels(); 
      this.loadFriends();
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
    if (!this.userId) return;

    this.friendApi.getFriends(this.userId).subscribe(
      (result: any) => {

        this.friends = result;
      },
      (error: any) => {
        console.error('Error loading friends:', error);
      }
    );
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
    
}