import { AlertServiceService } from './../alert-service.service';
import { UserService } from './../user.service';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { ChangePassword } from '../models/changePassword';
import { ApiResponse } from '../models/ApiResponse';
import { Modal } from 'bootstrap';


@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})

export class AdminPanelComponent implements OnInit {

  username ?: string;
  role ?: string;

  passwordModify : ChangePassword = new ChangePassword();

  constructor(private router: Router, private userService: UserService, private alertServiceService: AlertServiceService){}

  ngOnInit(): void {
    this.test();
  }

  test(){
    this.username = JSON.parse(localStorage.getItem("resp")!).name ;
    this.role  = JSON.parse(localStorage.getItem("resp")!).role;
  }

  logout(){
    localStorage.removeItem("token");
    this.router.navigateByUrl("home");
  }


isModalOpen = false;
openModal() {
  console.log("inside open modal");
  this.isModalOpen = true; }
closeModal() { this.isModalOpen = false; }


  changePassword(){
    console.log("oldapass", this.passwordModify);
    const oldPassword = this.passwordModify.oldPassword!;
    const newPassword = this.passwordModify.newPassword!;
    const confirmPassword = this.passwordModify.confirmPassword!;

     this.userService.changePassword(oldPassword!,newPassword,confirmPassword).subscribe((resp:ApiResponse) => {
            this.alertServiceService.showAlert('success', resp.message);
            this.closeModal();
         },
        error => {
            this.alertServiceService.showAlert('error', error.error.message);
        });
  }

  // onSubmit(){

  //   const oldPassword = (document.getElementById('oldPassword') as HTMLInputElement)?.value || '';
  //   const newPassword = (document.getElementById('newPassword') as HTMLInputElement)?.value || '';
  //   const confirmPassword = (document.getElementById('confirmPassword') as HTMLInputElement)?.value || '';

  //   console.log(oldPassword,newPassword,confirmPassword);
  //   this.userService.changePassword(oldPassword, newPassword,confirmPassword).subscribe({
  //     next: (res: any) => {
  //         if (res.success) {
  //           alert(res.message);
  //         } else {
  //           alert('Failed: ' + res.message);
  //         }
  //       },
  //       error: (err) => {
  //         alert('Error: ' + err.error.message || 'Something went wrong');
  //       }
  //   });
  // }
}
