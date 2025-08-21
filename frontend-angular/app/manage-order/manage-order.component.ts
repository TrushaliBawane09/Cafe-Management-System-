import { Bills } from './../models/Bills';
import { CartProduct } from './../models/CartProduct';
import { Product } from './../models/Product';
import { Category } from './../models/Category';
import { UserService } from './../user.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-manage-order',
  templateUrl: './manage-order.component.html',
  styleUrls: ['./manage-order.component.css']
})
export class ManageOrderComponent implements OnInit{

categories: Category[] =[];
products : Product[] =[];

cartProduct: CartProduct = new CartProduct();

cartProductList: CartProduct[] =[];
  bills: Bills = {
  name: '',
  email: '',
  contactNumber: '',
  paymentMode: '',
  productDetails: ''
};

  selectedCategoryId: number | null = null;  // <-- Declare here
  selectedProductId: number | null = null;   // <-- Also for product
  filteredProducts: any[] = [];

  ngOnInit(): void {
    this.getBillCategory();
    this.getBillProduct();
  }
  constructor(private userService: UserService) {}



  getBillCategory(){
    console.log("inside bill catgeory ");
    return this.userService.getBillCategory().subscribe(data =>{
      console.log(data);
      this.categories= data;
      console.log("categoeri" , this.categories);
    });
  }

  getBillProduct(){
    return this.userService.getBillProduct().subscribe(data =>{
      console.log(data);
      this.products = data;
    });
  }

  onCategoryChange(){
    console.log("onchange func");
    console.log(this.selectedCategoryId);
   this.filteredProducts =  this.products.filter(p => p.categoryId ===  this.selectedCategoryId);
   console.log(this.filteredProducts);
  }

  addProduct(){
    //console.log("category name :" , this.selectedCategoryId);
     //console.log("product name :" , this.selectedProductId);
      var newProduct:any;
     const categoryName = this.categories.find(c => c.id ===  this.selectedCategoryId)?.name;
     const productName = this.products.find(p => p.id === this.selectedProductId)?.name;
     console.log(categoryName+ " " +productName);

      newProduct ={
      categoryName: categoryName,
      productName: productName,
      price: this.cartProduct.price,
      quantity: this.cartProduct.quantity,
      total: this.cartProduct.total
     }

    this.cartProductList.push(newProduct);
    console.log("list ", this.cartProductList);
  }

  generateBill(){
    console.log(this.bills);
    console.log(this.cartProductList);

    const name = this.bills.name;
    const email = this.bills.email;
    const contactNumber = this.bills.contactNumber;
    const paymentMode = this.bills.paymentMode;
    const total = this.bills.total;
   this.bills.productDetails = JSON.stringify(this.cartProductList);
    console.log("bills" ,this.bills);
    console.log("productDetails", this.cartProductList)
    this.userService.generateBill(this.bills).subscribe(data =>{
      console.log("data", data);
    });
  }

}
