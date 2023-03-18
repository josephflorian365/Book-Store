package pe.todotic.bookstoreapi_s3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class BookstoreapiS3Application {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreapiS3Application.class, args);
	}

}
