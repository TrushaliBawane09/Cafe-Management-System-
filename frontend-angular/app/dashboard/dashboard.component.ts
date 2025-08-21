import { Dashboard } from './../models/Dashboard';
import { UserService } from './../user.service';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { ChangePassword } from '../models/changePassword';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  ngOnInit(): void {
    this.getDashboard();
  }
  changePasswordReq : ChangePassword = new ChangePassword();
   //loginReq : Login = new Login();
   dashboard : Dashboard = new Dashboard();

  constructor(private router : Router,private userService: UserService) {}


  getDashboard(){
    console.log("dashboard");

    this.userService.getDashboard().subscribe ({
         next: (data) => {
          console.log("data" , data);
          this.dashboard = data;
        },
        error: (err) => {
          console.error('Error fetching users:', err);
        }
      });
  }

}
