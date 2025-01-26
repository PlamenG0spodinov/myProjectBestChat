import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { Observable } from 'rxjs';
import { ChannelType } from "../models/channel.model";

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

    public inviteFriendToChannel(channelId: number, friendId: number): Observable<any> {
      return this.http.post(`${this.baseUrl}/channels/${channelId}/invite/${friendId}`, {});
    }
}
