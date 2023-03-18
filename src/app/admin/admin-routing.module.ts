import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookListComponent } from './books/book-list/book-list.component';
import { NewBookComponent } from './books/new-book/new-book.component';
import { EditBookComponent } from './books/edit-book/edit-book.component';
import { UserListComponent } from './users/user-list/user-list.component';
import { NewUserComponent } from './users/new-user/new-user.component';
import { EditUserComponent } from './users/edit-user/edit-user.component';
import { AdminLayoutComponent } from './admin-layout/admin-layout.component';

const routes: Routes = [
  {
    path: '',
    component: AdminLayoutComponent,
    children: [{
      path: 'books',
      component: BookListComponent
    },
    {
      path: 'books/new',
      component: NewBookComponent
    },
    {
      path: 'books/:id',
      component: EditBookComponent
    },
    {
      path: 'users',
      component: UserListComponent
    },
    {
      path: 'users/new',
      component: NewUserComponent
    },
    {
      path: 'users/:id',
      component: EditUserComponent
    }]
  }
    // path: '',
    // component: AdminLayoutComponent,
    // children: [
      
    // ]
    
  
  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
