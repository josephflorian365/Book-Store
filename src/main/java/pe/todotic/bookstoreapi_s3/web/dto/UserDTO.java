package pe.todotic.bookstoreapi_s3.web.dto;

import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class UserDTO {
    @NotNull
    @Size(min = 1, max = 45)
    private String firstName;
    @NotNull
    @Size(min = 1, max = 45)
    private String lastName;
    @NotNull
    @Size(min = 1, max = 100)
    private String fullName;
    @NotNull
    @Size(min = 1, max = 45)
    @Email
    //@UniqueEmail
    private String email;
    @NotNull
    @Size(min = 8, max = 45)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$")
    private String password;
    @NotNull
    @Pattern(regexp = "ADMIN|USER")
    private String role;

    //private Integer userId;
}
