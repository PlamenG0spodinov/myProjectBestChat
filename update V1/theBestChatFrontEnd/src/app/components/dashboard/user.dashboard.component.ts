import { Component, inject } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { FormsModule } from '@angular/forms';
import { ChannelApi } from "../../service/channel.api";
import { FriendApi } from "../../service/friends.api";


@Component({
    selector: 'app-user-dashboard',
    standalone: true,
    templateUrl: './user.dashboard.component.html',
    styleUrls: ['./user.dashboard.component.css'],
    imports: [FormsModule]
  })
  export class UserDashboardComponent {
  
    public userId: string | null = null;
    public userChannels: any[] = [];
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
      (result: any) => {
        this.userChannels = result; 
      },
      (error: any) => {
        console.error('Error loading channels:', error); 
      }
    );
  }

  public createChannel()  {
    if (!this.newChannelName.trim()) {
      return;
    }

    const channelData = {
      name: this.newChannelName,
      ownerId: this.userId
    };

    this.channelApi.createChannel(channelData).subscribe(() => {
      this.newChannelName = '';
      this.loadUserChannels(); 
    });
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