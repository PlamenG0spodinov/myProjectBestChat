import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
  })
  export class FriendApi {
    private baseUrl = 'http://localhost:8090';
  
    constructor(private http: HttpClient) {}
  
    public getFriends(userId: string): Observable<any> {
      return this.http.get(`${this.baseUrl}/friends/${userId}`);
    }
  
    public addFriendByUsername(userId: number, username: string): Observable<any> {
        return this.http.post(`${this.baseUrl}/friends/add/${userId}/${username}`, null);
      }
  }