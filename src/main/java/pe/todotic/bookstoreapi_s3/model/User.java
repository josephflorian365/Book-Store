package pe.todotic.bookstoreapi_s3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "fullname")
    private String fullName;

    private String email;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum Role {
        ADMIN, // 0
        USER // 1
    }

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now();
        fullName = firstName + " " + lastName;
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = LocalDateTime.now();
        fullName = firstName + " " + lastName;
    }
}
