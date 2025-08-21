import { UserService } from './../user.service';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { UserWrapper } from '../models/UserWrapper';

@Component({
  selector: 'app-manage-user',
  templateUrl: './manage-user.component.html',
  styleUrls: ['./manage-user.component.css']
})
export class ManageUserComponent implements OnInit{

  users : UserWrapper[] = [];
  id:number = 0;
  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.getUser();
  }

  getUser(){
    console.log("kkkkkk");
    this.userService.getUsers().subscribe ({
       next: (data) => {
        console.log("data" , data);
        this.users = data;
      },
      error: (err) => {
        console.error('Error fetching users:', err);
      }
    });
  }

  toggleStatus(user: any) {
    const id:number  = user.id;
    const status : boolean = !user.status;
    console.log(this.id);
    console.log(status);
    return this.userService.updateStatus(id,status).subscribe({
      next: () => console.log('Status updated'),
      error: (err) => console.error('Error updating status', err)
    })
  }
  // Optional: Call API to update the status in backend
  // this.userService.updateUserStatus(user.id, user.status).subscribe({
  //   next: () => console.log('Status updated'),
  //   error: (err) => console.error('Error updating status', err)
  // });

}
