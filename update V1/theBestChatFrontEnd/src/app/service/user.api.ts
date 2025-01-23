import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";



@Injectable({
    providedIn: 'root'
  })

export class UserApi {

    private baseUrl = `http://localhost:8090`;

    private http = inject(HttpClient)

  public getAllUsers() {
    return this.http.get(`${this.baseUrl}/user`);
  }
}