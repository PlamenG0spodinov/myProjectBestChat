import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { Observable } from 'rxjs';
import { ChannelType } from "../models/channel.model";
import { ChannelMemberType } from "../models/channel-member.model";

@Injectable({
    providedIn: 'root'
  })

export class ChannelApi {

    private baseUrl = `http://localhost:8090`

    private http = inject(HttpClient)

    public getUserChannels(userId: string): Observable<ChannelType[]> {
      return this.http.get<ChannelType[]>(`${this.baseUrl}/user/${userId}`);
    }
  
    public createChannel(channelData: ChannelType): Observable<any> {
      return this.http.post(`${this.baseUrl}/user/channels`, channelData);
    }

    public updateChannel(channelId: number, newName: string): Observable<any> {
      return this.http.put<any>(`${this.baseUrl}/update/${channelId}`, { name: newName });
    }
  
    public deleteChannel(channelId: number): Observable<any> {
      return this.http.delete<any>(`${this.baseUrl}/delete/${channelId}`);
    }

    public inviteFriendToChannel(channelId: number, friendId: number): Observable<any> {
      return this.http.post(`${this.baseUrl}/channels/${channelId}/invite/${friendId}`, {});
    }
    
    public getChannelsByInvitedUser(userId: number): Observable<ChannelMemberType[]> {
    
      return this.http.get<ChannelMemberType[]>(`${this.baseUrl}/user/${userId}/invited/channels`); 
    }
}
