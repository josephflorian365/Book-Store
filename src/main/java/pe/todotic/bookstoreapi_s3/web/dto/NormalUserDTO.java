package pe.todotic.bookstoreapi_s3.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NormalUserDTO {
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
    private String email;
    @NotNull
    @Size(min = 8, max = 45)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$")
    private String password;

    private String role = "USER";
}
