package pe.todotic.bookstoreapi_s3.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.todotic.bookstoreapi_s3.model.Book;
import pe.todotic.bookstoreapi_s3.model.SalesItem;
import pe.todotic.bookstoreapi_s3.model.SalesOrder;
import pe.todotic.bookstoreapi_s3.repository.BookRepository;
import pe.todotic.bookstoreapi_s3.repository.SalesOrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SalesOrderService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    public SalesOrder create(List<Integer> bookIds) {
        SalesOrder salesOrder = new SalesOrder();
        List<SalesItem> items = new ArrayList<>();
        float total = 0;

        for (int bookId : bookIds) {

            Book book = bookRepository
                    .findById(bookId)
                    .orElseThrow(EntityNotFoundException::new);

            SalesItem salesItem = new SalesItem();
            salesItem.setBook(book);
            salesItem.setPrice(book.getPrice());
            salesItem.setDownloadsAvailable(3);
            salesItem.setOrder(salesOrder);

            total += salesItem.getPrice();
            items.add(salesItem);
        }
        salesOrder.setItems(items);
        salesOrder.setTotal(total);
        salesOrder.setPaymentStatus(SalesOrder.PaymentStatus.PENDING);
        salesOrder.setCreatedAt(LocalDateTime.now());

        return salesOrderRepository.save(salesOrder);
    }
}
