package pe.todotic.bookstoreapi_s3.web;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import pe.todotic.bookstoreapi_s3.exception.BadRequestException;
import pe.todotic.bookstoreapi_s3.model.Book;
import pe.todotic.bookstoreapi_s3.model.SalesItem;
import pe.todotic.bookstoreapi_s3.model.SalesOrder;
import pe.todotic.bookstoreapi_s3.repository.BookRepository;
import pe.todotic.bookstoreapi_s3.repository.SalesItemRepository;
import pe.todotic.bookstoreapi_s3.repository.SalesOrderRepository;
import pe.todotic.bookstoreapi_s3.service.PaypalService;
import pe.todotic.bookstoreapi_s3.service.SalesOrderService;
import pe.todotic.bookstoreapi_s3.service.StorageService;
import pe.todotic.bookstoreapi_s3.web.paypal.OrderCaptureResponse;
import pe.todotic.bookstoreapi_s3.web.paypal.OrderResponse;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class HomeController {

    private final BookRepository bookRepository;
    private final SalesOrderRepository salesOrderRepository;
    private final PaypalService paypalService;
    private final SalesOrderService salesOrderService;
    private final SalesItemRepository salesItemRepository;
    private final StorageService storageService;

    @GetMapping("/last-books")
    List<Book> getLastBooks() {
//        Pageable pageable = PageRequest.of(0, 6, Sort.by("createdAt").descending());
//        return bookRepository.findAll(pageable).getContent();
        return bookRepository.findTop6ByOrderByCreatedAtDesc();
    }

    @GetMapping("/books")
    Page<Book> getBook(
            @PageableDefault(sort = "title") Pageable pageable
    ) {
        return bookRepository.findAll(pageable);
    }

    @GetMapping("/books/{slug}")
    Book getBook(
            @PathVariable String slug
    ) {
        return bookRepository.findOneBySlug(slug).orElseThrow(EntityExistsException::new);
    }

    @GetMapping("/orders/{id}")
    SalesOrder getBook(
            @PathVariable Integer id
    ) {
        return salesOrderRepository.findById(id).orElseThrow(EntityExistsException::new);
    }

    @PostMapping("/checkout/paypal/create")
    Map<String, String> createPaypalCheckout(
            @RequestBody List<Integer> bookIds,
            @RequestParam String returnUrl
    ) {
        SalesOrder salesOrder = salesOrderService.create(bookIds);
        OrderResponse orderResponse = paypalService.createOrder(salesOrder, returnUrl, returnUrl);
        String approveUrl = orderResponse
                .getLinks()
                .stream()
                .filter(link -> link.getRel().equals("approve"))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getHref();

        return Map.of("approveUrl", approveUrl);
    }

    @PostMapping("/checkout/paypal/capture")
    Map<String, Object> capturePaypalCheckout(@RequestParam String token) {
        OrderCaptureResponse orderCaptureResponse = paypalService.captureOrder(token);
        boolean completed = orderCaptureResponse.getStatus().equals("COMPLETED");
        int orderId = 0;

        if (completed) {
            orderId = Integer.parseInt(orderCaptureResponse.getPurchaseUnits().get(0).getReferenceId());
            SalesOrder salesOrder = salesOrderRepository
                    .findById(orderId)
                    .orElseThrow(RuntimeException::new);
            salesOrder.setPaymentStatus(SalesOrder.PaymentStatus.PAID);
            salesOrderRepository.save(salesOrder);
        }
        return Map.of("completed", completed, "orderId", orderId);
    }

    @GetMapping("/orders/{orderId}/items/{itemId}/book/download")
    Resource downloadBookFromSalesItem(
            @PathVariable Integer orderId,
            @PathVariable Integer itemId
    ) {
        SalesOrder salesOrder = salesOrderRepository
                .findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        if (!salesOrder.getPaymentStatus().equals(SalesOrder.PaymentStatus.PAID)) {
            throw new BadRequestException("The order hasn't been paid yet.");
        }
        SalesItem salesItem = salesItemRepository
                .findOneByIdAndOrderId(itemId, orderId)
                .orElseThrow(EntityNotFoundException::new);

        if (salesItem.getDownloadsAvailable() > 0) {
            salesItem.setDownloadsAvailable(
                    salesItem.getDownloadsAvailable() - 1
            );
            salesItemRepository.save(salesItem);
        } else {
            throw new BadRequestException("Can't download this file anymore.");
        }
        return storageService.loadAsResource(salesItem.getBook().getFilePath());
    }

}
