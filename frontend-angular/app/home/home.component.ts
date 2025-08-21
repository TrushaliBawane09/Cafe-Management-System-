import { AlertServiceService } from './../alert-service.service';
import { Component } from '@angular/core';
import { User } from '../models/signup.model';
import { UserService } from '../user.service';
import { Login } from '../models/Login';
import { Router } from '@angular/router';
import { ApiResponse } from '../models/ApiResponse';
import { Form, NgForm } from '@angular/forms';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent {

  user: User = new User();
  loginReq : Login = new Login();
  activeForm: 'login' | 'signUp' | 'forgot' = 'login';
  isLoginVisible= true;

  constructor(private userService: UserService, private router: Router, private alertServiceService: AlertServiceService) {}

  ngOnInit(): void {

  }

   showSignUp() {
    this.activeForm = 'signUp';
  }

  showLogin() {
    this.activeForm = 'login';
  }

  showForgotPassword() {
    this.activeForm = 'forgot';
  }

  sendResetLink(){

  }

  register(form:NgForm) {
    console.log(this.user);
    this.userService.signUp(this.user).subscribe((resp:ApiResponse) => {
        this.alertServiceService.showAlert('success', resp.message);
        form.resetForm();
     },
    error => {
        this.alertServiceService.showAlert('error', error.error.message);
    });
  }

  login(){
          console.log("inside log");
     this.userService.login(this.loginReq).subscribe((resp:ApiResponse) =>{
      let message = 'some thing went wrong';
      if(resp.statusCode === 200){
          this.alertServiceService.showAlert("success", message= "Login successfully ");
          console.log('respp', resp);
          localStorage.setItem("resp",JSON.stringify(resp.message));
          let token = JSON.parse(localStorage.getItem("resp")!).token;
          localStorage.setItem("token", token);
          //let a = localStorage.getItem("token");
          this.router.navigateByUrl("/admin/dashboard");
      }
     },
      error => {
        console.log("err", error);
        if(error.statusCode == 400){
            this.alertServiceService.showAlert("warning", error.error.message);
        }
        this.alertServiceService.showAlert('error', error.error.message);
      });

  }
}
