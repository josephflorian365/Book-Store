import { Component, OnInit } from '@angular/core';
import { Book, BookPage } from 'src/app/admin/books/shared/book.model';
import { CartService } from '../shared/cart.service';
import { HomeService } from '../shared/home.service';

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html'
})
export class BooksComponent implements OnInit {
  books: Book[] = [];
  page: number = 0;

  constructor(
    private homeService: HomeService
  ){}

  ngOnInit(): void {
    this.homeService.getBooks()
    .subscribe( bookPage => {
      this.books = bookPage.content;
      this.page = bookPage.number;
    });
  }

  loadMoreBooks(){
    this.homeService.getBooks(this.page + 1)
    .subscribe( bookPage => {
      this.books.push(...bookPage.content);
      this.page = bookPage.number;
    });
  }
}
