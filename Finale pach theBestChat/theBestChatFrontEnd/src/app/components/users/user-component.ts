import { Component, inject } from "@angular/core";
import { Router} from "@angular/router";
import { UserApi } from "../../service/user.api";
import { UserType } from "../../models/user.model";
import { FormsModule } from "@angular/forms";




@Component({
  selector: 'app-user',
  standalone: true,
  templateUrl: './user-component.html',
  styleUrl: './user-compnent.css',
  imports: [FormsModule]
})
export class UserComponent {
  public userCollection: any = [];
  public newUser: UserType = { username: '', email: '', password: '' };

  private userApi = inject(UserApi);
  private router = inject(Router);



  public ngOnInit(){
    this.fetchAllUsers();
  }
 
  public fetchAllUsers() {
   this.userApi.getAllUsers().subscribe((result: any) =>{
    this.userCollection = result.data;
   })
  }

  public loginAsUser(user: UserType) {
    this.router.navigate(['/dashboard', user.id]);
  }

  public createUser() {
    if (!this.newUser.username.trim() || !this.newUser.email.trim() || !this.newUser.password.trim()) {
      return;
    }

    this.userApi.createUser(this.newUser).subscribe(
      () => {
        this.newUser = { username: '', email: '', password: '' }; 
        this.fetchAllUsers(); 
      });
  }
}
