import { Book } from "src/app/admin/books/shared/book.model";

export interface SalesOrder {
    id:            number;
    total:         number;
    createdAt:     Date;
    paymentStatus: string;
    customer:      null;
    items:         SalesItem[];
}

export interface SalesItem {
    id:                 number;
    price:              number;
    downloadsAvailable: number;
    book:               Book;
}
