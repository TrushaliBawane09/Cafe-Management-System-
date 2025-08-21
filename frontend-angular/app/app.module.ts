import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { ContactComponent } from './contact/contact.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AuthInterceptor } from '../app/AuthInterceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { ManageCategoryComponent } from './manage-category/manage-category.component';
import { ManageUserComponent } from './manage-user/manage-user.component';
import { ManageProductComponent } from './manage-product/manage-product.component';
import { ManageOrderComponent } from './manage-order/manage-order.component';
import { ManageBillComponent } from './manage-bill/manage-bill.component';
import { AdminPanelComponent } from './admin-panel/admin-panel.component';
import { EditCategoryComponent } from './edit-category/edit-category.component';
import { MenuComponent } from './menu/menu.component';
import { ServicesComponent } from './services/services.component';
import { AuthGuard } from './AuthGuard';
import { ManageAdminsComponent } from './manage-admins/manage-admins.component';


const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'home', component: HomeComponent},
  {path: '' , redirectTo: 'home', pathMatch: 'full'} , //it means when nothing will in UI it will redirect to home
  { path: 'logout' , component: HomeComponent},
  { path: 'menu', component: MenuComponent},
  { path: 'about', component:  AboutComponent},
  { path:  'services', component: ServicesComponent},
  { path:  'contact', component: ContactComponent},

  {
    path: 'admin/dashboard',
    component: AdminPanelComponent,canActivate: [AuthGuard],
    children: [
      { path: 'dashboard', component: DashboardComponent },
      { path: 'manage-user', component: ManageUserComponent },
      { path: 'manage-product', component: ManageProductComponent },
      { path: 'manage-category',
        component: ManageCategoryComponent ,
         children:[
          {path: 'editCategory/:id' , component: EditCategoryComponent}
        ]
      },
      { path: 'manage-order', component: ManageOrderComponent },
      { path: 'manage-bill', component: ManageBillComponent },
      { path: 'manage-admin', component: ManageAdminsComponent },
      { path: 'view-bill' , component: ManageBillComponent},
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }, // Default page
    ]
  }

]

@NgModule({
  declarations: [
    AppComponent,
    ContactComponent,
    HomeComponent,
    AboutComponent,
    DashboardComponent,
    ManageCategoryComponent,
    ManageUserComponent,
    ManageProductComponent,
    ManageOrderComponent,
    ManageBillComponent,
    AdminPanelComponent,
    EditCategoryComponent,
    ManageAdminsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(routes),
    HttpClientModule,
    FormsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true // VERY important: allows multiple interceptors
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
