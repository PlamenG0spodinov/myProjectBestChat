import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
  })

export class ChannelApi {

    private baseUrl = `http://localhost:8090`

    private http = inject(HttpClient)

    public getUserChannels(userId: string): Observable<any> {
        return this.http.get(`${this.baseUrl}/user/${userId}`);
      }
    
      public createChannel(channelData: any): Observable<any> {
        return this.http.post(`${this.baseUrl}/user/channels`, channelData);
      }
  }
