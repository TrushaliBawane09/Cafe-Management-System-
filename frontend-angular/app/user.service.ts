import { Bills } from './models/Bills';
import { Category } from './models/Category';
import { ChangePassword } from './models/changePassword';
import { Login } from './models/Login';
import { Injectable } from '@angular/core';
import { environment } from './environments';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from './models/signup.model';
import { UserWrapper } from './models/UserWrapper';
import { Product } from './models/Product';
import { Dashboard } from './models/Dashboard';
import { ApiResponse } from './models/ApiResponse';
import { PageResponse } from './models/PageResponse';


@Injectable({
  providedIn: 'root'
})
export class UserService {

    uri = environment.apiUrl;

  constructor(private httpClient: HttpClient) { }


   signUp(user: User): Observable<Object> {
      console.log('url' , this.uri);
      //const headers = new HttpHeaders({'Content-type' : 'application/json'});
            console.log(this.uri +"/auth/signup");
      return this.httpClient.post(this.uri + "/auth/signup" , user);
    }


    login(login : Login):Observable<ApiResponse>{

      return this.httpClient.post<ApiResponse>(this.uri + "/auth/login", login );
    }

    changePassword(oldPassword: string ,newPassword: string , confirmPassword :string): Observable<Object>{
      const requestBody = {
        oldPassword: oldPassword,
        newPassword: newPassword,
        confirmPassword: confirmPassword
      };

      console.log("inside changepassword");
      return this.httpClient.post(this.uri + "/admin/change-password", requestBody);
    }

    getUsers(): Observable<UserWrapper[]>{
      console.log("oooo");
      return this.httpClient.get<UserWrapper[]>(this.uri + "/admin/users");
    }

    updateStatus(id: number, status: boolean): Observable<Object>{
      return this.httpClient.put<Object>(`${this.uri}/admin/status/${id}/${status}`, {});
    }

    addCategory(category: Category): Observable<Object>{
      console.log('category' , category);
      return this.httpClient.post<Object>(`${this.uri}/category/create` , category);
    }

    getAllCategory(page : number, pageSize : number): Observable<PageResponse<Category>>{
      return this.httpClient.get<PageResponse<Category>>(`${this.uri}/category?page=${page}&pageSize=${pageSize}`);
    }

    updateCategory(id: number, category: Category): Observable<Object>{
      return this.httpClient.put<Object>(`${this.uri}/category/${id}`, category);
    }

    deleteCategory(id:number): Observable<Object> {
      return this.httpClient.delete(`${this.uri}/category/${id}`);
    }

    getAllProduct(): Observable<Product[]>{
      return this.httpClient.get<Product[]>(`${this.uri}/product` );
    }
    getAllCategories(): Observable<Category[]>{
      return this.httpClient.get<Category[]>(`${this.uri}/product/getCategories`);
    }

    addProduct(product:Product, image :File): Observable<ApiResponse>{
     const formData = new FormData();
     formData.append('product', new Blob([JSON.stringify(product)],{type: 'application/json'}));
     formData.append('image', image);
      return this.httpClient.post(`${this.uri}/product`,formData);
    }

    getDashboard(): Observable<Object>{
     return  this.httpClient.get<Object>(`${this.uri}/admin/counts`);
    }

    getBillCategory(): Observable<Category[]>{
      return this.httpClient.get<Category[]>(`${this.uri}/bills/getCategories`);
    }

    getBillProduct(): Observable<Product[]>{
      return this.httpClient.get<Product[]>(`${this.uri}/bills/product`);
    }

    generateBill(bills:Bills): Observable<Object>{
        return this.httpClient.post(`${this.uri}/bills/generate-bill`, bills);
    }

    searchCategory(searchItem: string): Observable<Category[]>{
      console.log(`${this.uri}/category/search-category/${searchItem}`);
      return this.httpClient.get<Category[]>(`${this.uri}/category/search-category/${searchItem}`);
    }

    updateRole(id : number , role: string): Observable<any>{
      return this.httpClient.put<any>(`${this.uri}/admin/update-role/${id}/${role}` , {} );
    }

    updateProduct(id : number, product: Product):Observable<Object>{
      return this.httpClient.post(`${this.uri}/product/update-product/${id}`, product);
    }

     deleteProduct(id:number): Observable<any> {
      return this.httpClient.delete(`${this.uri}/product/delete-product/${id}`);
    }

    getAllBills():Observable<Bills[]>{
      return this.httpClient.get<Bills[]>(`${this.uri}/bills/getBills`);
    }

    deleteBill(id :number):Observable<Object>{
      return this.httpClient.delete<Object>(`${this.uri}/bills/delete-bill/${id}`);
    }
}



