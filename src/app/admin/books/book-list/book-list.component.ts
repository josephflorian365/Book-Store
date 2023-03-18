import { Component, OnInit } from '@angular/core';
import { BookService } from '../shared/book.service';
import { Book, BookPage } from '../shared/book.model';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {
  bookPage?: BookPage;
  displayedColumns: string[] = ['title', 'price', 'createdAt', 'actions'];


  constructor(
    private bookService: BookService
  ) { }

  ngOnInit(): void {
    this.getBooks();
  }

  getBooks() {
    this.bookService.paginate()
      .subscribe(bookPage => {
        this.bookPage = bookPage;
      })
  }

  deleteBook(book: Book) {
    if (confirm('¿Estás seguro de eliminar este libro?')) {
      this.bookService.delete(book.id!)
        .subscribe(() => {
          this.getBooks();
        });
    }
  }

  paginateBooks(event: PageEvent) {
    const page = event.pageIndex;
    const size = event.pageSize;

    this.bookService.paginate(size, page)
      .subscribe(bookPage => {
        this.bookPage = bookPage;
      })
  }

}
