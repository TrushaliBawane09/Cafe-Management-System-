import { AlertServiceService } from './../alert-service.service';
import { Router } from '@angular/router';
import { Category } from './../models/Category';
import { UserService } from './../user.service';
import { Component, OnInit } from '@angular/core';
import { Modal } from 'bootstrap';
import { ApiResponse } from '../models/ApiResponse';
declare var bootstrap: any; // add this line if you're using UMD global


@Component({
  selector: 'app-manage-category',
  templateUrl: './manage-category.component.html',
  styleUrls: ['./manage-category.component.css']
})
export class ManageCategoryComponent implements OnInit {

totalPages: number = 0;
currentPage: number = 0;
pageSize: number = 10;

  ngOnInit(): void {
    this.getCategory(this.currentPage);
  }

  category:  Category = new Category();
  userId : number = 0;
  categoryList ?: Category[];
  searchTerm = '';
  isCategoryModal = false;
  isEditCategoryModal = false;

  constructor(private userService: UserService, private router: Router, private alertServiceService :AlertServiceService) {}

  openCategoryModal(){
    this.isCategoryModal = true;
  }

  closeCategoryModal(){
    this.isCategoryModal = false;
  }

  addCategory(){
    console.log(this.category);
     this.userService.addCategory(this.category).subscribe((resp:ApiResponse) =>{
      console.log("resp", resp);
      if(resp.statusCode === 200){
          this.alertServiceService.showAlert("success", resp.message);
          this.closeCategoryModal();
          this.getCategory();
      }
     },
      error => {
        console.log("err", error);
        if(error.statusCode == 400){
            this.alertServiceService.showAlert("warning", error.message);
        }
      });
  }

  getCategory(page: number = 0){
    console.log("page", page);
    this.currentPage = page;
    this.userService.getAllCategory(page,this.pageSize).subscribe({
      next: (data) => {
        console.log("data" , data);
        this.categoryList = data.content;
        this.totalPages = data.totalPages!;
        this.currentPage = data.number!;
      },
      error: (err) => {
        console.error('Error fetching users:', err);
      }
    });
  }

  openEditCategoryModal(categoryEdit : Category){
    this.isEditCategoryModal = true;
    this.userId =categoryEdit.id!;
  this.category = categoryEdit;
  }

  closeEditCategoryModal(){
    this.isEditCategoryModal = false;
  }


updateCategory(userId : number){
  this.userService.updateCategory(userId, this.category).subscribe((resp : ApiResponse) =>{
    if(resp.statusCode === 200){
      this.alertServiceService.showAlert("success", resp.message);
      this.closeEditCategoryModal();
    }
  },
  error => {
    this.alertServiceService.showAlert("error", error.message);
  }
  );
}

deleteCategory(id: number) {
  this.alertServiceService.showDeleteConfirm("Do you really want to delete this category?")
    .then((result) => {
      if (result.isConfirmed) {
        this.userService.deleteCategory(id).subscribe({
          next: (data) => {
            console.log("Deleted:", data);
            this.alertServiceService.showAlert("success", "Category deleted successfully!");
            this.getCategory();
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


searchCategory(){
  const searchItem = this.searchTerm.trim();
  if(searchItem === ''){
    this.getCategory();
  }
  this.userService.searchCategory(this.searchTerm).subscribe(data =>{
    console.log("serachdata" , data);
    if(data.length === 0){
      this.categoryList =  [];
    }else{
    this.categoryList = data;
    }
  },
);
}


}
