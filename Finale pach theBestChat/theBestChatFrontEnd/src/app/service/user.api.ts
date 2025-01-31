import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { UserType } from "../models/user.model";



@Injectable({
    providedIn: 'root'
  })

export class UserApi {

    private baseUrl = `http://localhost:8090/user`;

    private http = inject(HttpClient)

    public getAllUsers() {
      return this.http.get<UserType[]>(`${this.baseUrl}`);
    }
  
    public createUser(user: UserType) {
      return this.http.post<UserType>(this.baseUrl, user);
    }
  
    public deleteUser(userId: number) {
      return this.http.delete(`${this.baseUrl}/${userId}`);
    }
  }
