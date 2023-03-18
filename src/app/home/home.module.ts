import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IndexComponent } from './index/index.component';
import { HomeLayoutComponent } from './home-layout/home-layout.component';
import { HomeRoutingModule } from './home-routing.module';
import { MaterialModule } from '../material/material.module';
import { SharedModule } from '../shared/shared.module';
import { BooksComponent } from './books/books.component';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { BookCardComponent } from './shared/book-card/book-card.component';
import { BookDetailComponent } from './book-detail/book-detail.component';
import { CartComponent } from './cart/cart.component';
import { OrderDetailComponent } from './order-detail/order-detail.component';



@NgModule({
  declarations: [
    IndexComponent,
    HomeLayoutComponent,
    BooksComponent,
    BookCardComponent,
    BookDetailComponent,
    CartComponent,
    OrderDetailComponent
  ],
  imports: [
    CommonModule,
    HomeRoutingModule,
    MaterialModule,
    SharedModule,
    InfiniteScrollModule
  ]
})
export class HomeModule { }
