import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { MessageType } from "../models/message.model";

@Injectable({
    providedIn: 'root'
  })
  export class MessageApi {
  
    private baseUrl = 'http://localhost:8090';  
  
    constructor(private http: HttpClient) { }
  
   
    public sendMessage(senderId: number, recipientId: number, messageContent: string): Observable<MessageType> {
      return this.http.post<MessageType>(`${this.baseUrl}/message/send/${senderId}/${recipientId}`, {messageContent});
    }
  
    public getMessages(userId1: number, userId2: number): Observable<any> {
      return this.http.get<any>(`${this.baseUrl}/message/chat/${userId1}/${userId2}`);
    }

    public sendMessageToChannel(senderId: number, channelId: number, channelMessageContent: string ): Observable<MessageType> {
      return this.http.post<MessageType>(`${this.baseUrl}/message/sendToChannel/${senderId}/${channelId}`, {channelMessageContent});
    }
  
    public getChannelMessages(channelId: number): Observable<any>  {
      return this.http.get<any>(`${this.baseUrl}/message/channel/${channelId}`);
    }
}