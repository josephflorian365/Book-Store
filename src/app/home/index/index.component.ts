import { Component, OnInit } from '@angular/core';
import { Book } from 'src/app/admin/books/shared/book.model';
import { CartService } from '../shared/cart.service';
import { HomeService } from '../shared/home.service';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html'
})
export class IndexComponent implements OnInit {
  books: Book[] = [];

  constructor(
    private homeService: HomeService
  ) {

  }

  ngOnInit(): void {
    this.homeService.getLastBooks()
      .subscribe(books => {
        this.books = books;
      })
  }
}
