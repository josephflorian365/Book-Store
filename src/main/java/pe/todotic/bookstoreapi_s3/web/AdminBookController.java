package pe.todotic.bookstoreapi_s3.web;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.todotic.bookstoreapi_s3.model.Book;
import pe.todotic.bookstoreapi_s3.repository.BookRepository;
import pe.todotic.bookstoreapi_s3.web.dto.BookDTO;

import java.util.List;

@RestController
@RequestMapping("/api/admin/books")
public class AdminBookController {

    @Autowired
    private BookRepository bookRepository;

    /**
     * Devuelve la lista completa de libros
     * Retorna el status OK: 200
     * Ej.: GET http://localhost:9090/api/books
     */
    @GetMapping("/list")
    List<Book> list() {
        return bookRepository.findAll();
    }

    /**
     * Devuelve un libro por su ID, en caso contrario
     * lanza EntityNotFoundException.
     * Retorna el status OK: 200
     * Ej.: GET http://localhost:9090/api/books/1
     */
    @GetMapping("/{id}")
    Book get(@PathVariable Integer id) {
        return bookRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Crea un libro a partir del cuerpo
     * de la solicitud HTTP y retorna
     * el libro creado.
     * Retorna el status CREATED: 201
     * Ej.: POST http://localhost:9090/api/books
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Book create(@Validated @RequestBody BookDTO bookDTO) {
//        Book book = new Book();
//        book.setTitle(bookDTO.getTitle());
//        book.setSlug(bookDTO.getSlug());
        //...
        Book book = new ModelMapper().map(bookDTO, Book.class);
        System.out.println("cover_Path " + book.getCoverPath() + " FilePath " + book.getFilePath());
        return bookRepository.save(book);
    }

    /**
     * Actualiza un libro por su ID, a partir
     * del cuerpo de la solicitud HTTP.
     * Si el libro no es encontrado se lanza EntityNotFoundException.
     * Retorna el status OK: 200.
     * Ej.: PUT http://localhost:9090/api/books/1
     */
    @PutMapping("/{id}")
    Book update(
            @PathVariable Integer id,
            @Validated  @RequestBody BookDTO bookDTO
    ) {
        Book book = bookRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);

//        book.setTitle(form.getTitle());
//        book.setSlug(form.getSlug());
//        book.setPrice(form.getPrice());
//        book.setDesc(form.getDesc());
        new ModelMapper().map(bookDTO, book);
        return bookRepository.save(book);
    }

    /**
     * Elimina un libro por su ID.
     * Si el libro no es encontrado se lanza EntityNotFoundException.
     * Retorna el status NO_CONTENT: 204
     * Ej.: DELETE http://localhost:9090/api/books/1
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        Book book = bookRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);

        bookRepository.delete(book);
    }

    /**
     * Devuelve la lista de libros de forma paginada.
     * El cliente puede enviar los parámetros page, size, sort,... en la URL
     * para configurar la página solicitada.
     * Si el cliente no envía ningún parámetro para la paginación,
     * se toma la configuración por defecto.
     * Retorna el status OK: 200
     * Ej.: GET http://localhost:9090/api/books?page=0&size=2&sort=createdAt,desc
     *
     * @param pageable la configuración de paginación que captura los parámetros como: page, size y sort
     */
    @GetMapping
    Page<Book> paginate(
            @PageableDefault(sort = "title", direction = Sort.Direction.ASC, size = 5) Pageable pageable
    ) {
        return bookRepository.findAll(pageable);
    }

}
