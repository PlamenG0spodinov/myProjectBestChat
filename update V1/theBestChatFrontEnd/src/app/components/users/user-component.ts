import { Component, inject } from "@angular/core";
import { Router} from "@angular/router";
import { UserApi } from "../../service/user.api";




@Component({
  selector: 'app-user',
  standalone: true,
  templateUrl: './user-component.html',
  styleUrl: './user-compnent.css'
})
export class UserComponent {
  public userCollection: any = [];

  private userApi = inject(UserApi);
  private router = inject(Router);



  public ngOnInit(){
    this.userApi.getAllUsers().subscribe(
    (result: any) => {this.userCollection = result.data})
  }
 
  public loginAsUser(user: any) {
    console.log('Logging in as:', user);
    this.router.navigate(['/dashboard', user.id]);
  }
}