import { User } from './../models/signup.model';
import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { UserWrapper } from '../models/UserWrapper';

@Component({
  selector: 'app-manage-admins',
  templateUrl: './manage-admins.component.html',
  styleUrls: ['./manage-admins.component.css']
})
export class ManageAdminsComponent implements OnInit{

   users : UserWrapper[] = [];
    id:number = 0;
    constructor(private userService: UserService) {}

    ngOnInit(): void {
      this.getUserList();
    }

    getUserList(){
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

    updateRole(user: any){
      console.log(`Changed role for ${user.name} to ${user.role}`);
       const id : number  = user.id;
      const roleObj : string = user.role;
       const role = roleObj.split("_")[1];
      console.log("role", role);
      this.userService.updateRole(id,role).subscribe({
        next: (resp) => console.log("role updated", resp),
        error: (err) => console.log("err while updating role ", err)
      })
    }

}
