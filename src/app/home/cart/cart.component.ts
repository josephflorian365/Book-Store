import { Token } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { Book } from 'src/app/admin/books/shared/book.model';
import { CartService } from '../shared/cart.service';
import { HomeService } from '../shared/home.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html'
})
export class CartComponent implements OnInit{
  loading= false;

  constructor(
    private cartService: CartService,
    private homeService: HomeService,
    private activateRoute: ActivatedRoute,
    private router: Router
  ){}

  ngOnInit(): void {
    const token = this.activateRoute.snapshot.queryParamMap.get('token');

    if(token){
      this.loading = true;

      this.homeService.capturePaypalCheckout(token)
      .subscribe(response => {
        if(response.completed){
          this.cartService.clear();
          this.router.navigate(["/orders",response.orderId])
        }
      })
    }
  }

  get items(){
    return this.cartService.items;
  }

  get total(){
    return this.items.map(i => i.price).reduce((p1, p2) => p1 + p2, 0);
  }

  remove(book: Book) {
    this.cartService.removeItem(book);
  }

  pay(){
    const bookIds = this.items.map(item => item.id!);
    this.loading = true;

    this.homeService.createPaypalCheckout(bookIds)
    .subscribe(response => {
      window.location = response.approveUrl;
    })
  }
}
