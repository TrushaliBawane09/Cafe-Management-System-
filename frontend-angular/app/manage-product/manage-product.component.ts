import { Product } from './../models/Product';
import { AlertServiceService } from './../alert-service.service';
import { Category } from './../models/Category';
import { UserService } from './../user.service';
import { Component, OnInit } from '@angular/core';
import { ApiResponse } from '../models/ApiResponse';

@Component({
  selector: 'app-manage-product',
  templateUrl: './manage-product.component.html',
  styleUrls: ['./manage-product.component.css']
})
export class ManageProductComponent implements OnInit{

  product : Product = new Product();
  constructor(private userService : UserService, private alertServiceService: AlertServiceService) {}
  productList ?: Product[] = [];
  selectedImage ?: File ;

  categories: Category[] = [];
  categoryName ?: string = '';
  updProduct : Product = new Product();

ngOnInit(): void {

  this.getAllProduct();

  this.userService.getAllCategories().subscribe(data =>{
    this.categories = data;
    console.log(this.categories);
  });

    this.categoryName = this.product.categoryDTO?.name || '';

}

getAllProduct() {
  this.userService.getAllProduct().subscribe({
    next: (data: Product[]) => {
      this.productList = data.map(p => ({
        ...p,
        imageUrl: p.imageBytes
          ? 'data:image/jpeg;base64,' + p.imageBytes
          : null
      }));
    },
    error: (err) => {
      console.error("Error fetching products:", err);
    }
  });
}



getAllCategories(){
  this.userService.getAllCategories().subscribe({
     next: (data) => {
      console.log(data);
      },
      error: (err) => {
        console.error('Error fetching users:', err);
      }
  });
}

selectedProductImage(event: Event) {
  const file = (event.target as HTMLInputElement)?.files?.[0] || null;
  this.selectedImage = file!;
}

isAddProductModal = false;

openAddProductModal(){
  this.isAddProductModal = true;
}

closeAddProductModal(){
  this.isAddProductModal = false;
}

addProduct(){
    console.log(this.product);
    console.log(this.selectedImage);
    this.userService.addProduct(this.product, this.selectedImage!).subscribe(resp =>{
      if(resp.statusCode === 200){
        this.alertServiceService.showAlert("success", resp.message);
        this.closeAddProductModal();
        this.getAllProduct();
      }
    },
    error =>{
      this.alertServiceService.showAlert("error", error);
    }
  );
}

isProductEditModal = false ;


  openEditProductModal(product: Product){

    this.updProduct = product;
    this.categoryName = this.product.categoryDTO?.name;
    console.log(this.product);
    this.isProductEditModal = true;
  }

  closeEditProductModal(){
    this.isProductEditModal = false;
  }

  updateProduct(){
    console.log(this.updProduct);
    console.log("id", this.updProduct.id, " ", this.updProduct);
    this.userService.updateProduct(this.updProduct.id!, this.updProduct).subscribe((resp:ApiResponse) =>{

        this.alertServiceService.showAlert("success", resp.message);
        this.closeEditProductModal();
    },
    error => {
      this.alertServiceService.showAlert("error", error);
    }
  );
  }

  deleteProduct(id : number){
    this.alertServiceService.showDeleteConfirm("Do you really want to delete this category?")
    .then((result) => {
      if (result.isConfirmed) {
        this.userService.deleteProduct(id).subscribe({
          next: (data) => {
            this.alertServiceService.showAlert("success", "Category deleted successfully!");
            this.getAllProduct();
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
