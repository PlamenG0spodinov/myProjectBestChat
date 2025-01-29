import { Routes } from '@angular/router';
import { UserDashboardComponent } from './components/dashboard/user-dashboard.component';
import { UserComponent } from './components/users/user-component';


export const routes: Routes = [

    {
        path: '', 
        redirectTo: 'login',
        pathMatch: 'full' 
      },
      {
        path: 'login', 
        component: UserComponent
      },
      {
        path: 'dashboard/:userId', 
        component: UserDashboardComponent
      }
];
