package pe.todotic.bookstoreapi_s3.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String slug;

    @Column(name = "description")
    private String desc;

    private Float price;

    private String coverPath;

    private String filePath;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    void initCreatedAt() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    void initUpdatedAt() {
        updatedAt = LocalDateTime.now();
    }

}
