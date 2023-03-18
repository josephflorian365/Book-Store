package pe.todotic.bookstoreapi_s3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.todotic.bookstoreapi_s3.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findOneByEmail(String email);
    @Query("SELECT COUNT(u) FROM User u WHERE u.email = :email AND u.id <> :id")
    public int countByEmailAndNotId(String email, Integer id);
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
