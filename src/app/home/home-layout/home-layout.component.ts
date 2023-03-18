import { Component } from '@angular/core';
import { CartService } from '../shared/cart.service';

@Component({
  selector: 'app-home-layout',
  templateUrl: './home-layout.component.html'
})
export class HomeLayoutComponent {

  constructor(
    private cartService: CartService
  ){}

  get cartItems(){
    return this.cartService.items;
  }
}
