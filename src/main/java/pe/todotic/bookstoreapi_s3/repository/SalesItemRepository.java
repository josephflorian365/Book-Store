package pe.todotic.bookstoreapi_s3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.todotic.bookstoreapi_s3.model.SalesItem;

import java.util.Optional;

public interface SalesItemRepository extends JpaRepository<SalesItem, Integer> {

    Optional<SalesItem> findOneByIdAndOrderId(Integer id, Integer orderId);
}
