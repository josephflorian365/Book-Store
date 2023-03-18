package pe.todotic.bookstoreapi_s3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.todotic.bookstoreapi_s3.model.Book;

import java.util.List;
import java.util.Optional;

//@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findTop6ByOrderByCreatedAtDesc();
    List<Book> findLast6ByOrderByCreatedAt();

    Optional<Book> findOneBySlug(String slug);

    @Query("select b from Book b where b.slug = ?1")
    Optional<Book> fbs(String s);
}
