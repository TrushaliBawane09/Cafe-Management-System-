import { AlertServiceService } from './../alert-service.service';
import { UserService } from './../user.service';
import { Bills } from './../models/Bills';
import { Component, OnInit } from '@angular/core';
import { ApiResponse } from '../models/ApiResponse';

@Component({
  selector: 'app-manage-bill',
  templateUrl: './manage-bill.component.html',
  styleUrls: ['./manage-bill.component.css']
})
export class ManageBillComponent  implements OnInit{

  bills : Bills = new Bills();
  billList : Bills[] =[];
  ngOnInit(): void {
    this.getAllBills();

  }

  constructor(private userService: UserService, private alertServiceService: AlertServiceService){}

  getAllBills(){
    this.userService.getAllBills().subscribe((resp: Bills[]) =>{
        console.log("bills", resp);
        this.billList = resp;
    },
    error =>{
      this.alertServiceService.showAlert("error", error);
    }
  );
  }

  deleteBill(id : number){
    this.alertServiceService.showDeleteConfirm("Do you really want to delete this category?")
    .then((result) => {
      if (result.isConfirmed) {
        this.userService.deleteBill(id).subscribe({
          next: (data) => {
            console.log("Deleted:", data);
            this.alertServiceService.showAlert("success", "Category deleted successfully!");
            this.getAllBills();
          },
          error: (err) => {
            console.error("Error deleting:", err);
            this.alertServiceService.showAlert("error", "Failed to delete category!");
          }
        });
      } else {
        console.log("User canceled delete");
      }
    });
  }

}
