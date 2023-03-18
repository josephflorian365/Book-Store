import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Book } from 'src/app/admin/books/shared/book.model';
import { CartService } from '../shared/cart.service';
import { HomeService } from '../shared/home.service';

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html'
})
export class BookDetailComponent implements OnInit{

  book?: Book;

  constructor(
    private homeService: HomeService,
    private activateRoute: ActivatedRoute,
    private cartService: CartService
  ){}

  ngOnInit(): void {
    const slug = this.activateRoute.snapshot.paramMap.get('slug')!;

    this.homeService.getBook(slug)
    .subscribe(book => {
      this.book = book;
    })
  }

  addBookToCart(book: Book){
    this.cartService.addItem(book);
  }

  removeBookFromCart(book: Book){
    this.cartService.removeItem(book);
  }

  bookExistsInCart(book: Book): boolean{
    return this.cartService.itemAlreadyExists(book);
  }

}
