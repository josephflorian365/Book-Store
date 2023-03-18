package pe.todotic.bookstoreapi_s3.web;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.todotic.bookstoreapi_s3.model.User;
import pe.todotic.bookstoreapi_s3.service.AccountService;
import pe.todotic.bookstoreapi_s3.web.dto.SignupUserDTO;

import java.security.Principal;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/me")
    User getMyInfo(Principal principal) {
        return accountService.getInfo(principal.getName());
    }

    @PostMapping("/signup")
    User signup(@RequestBody @Validated SignupUserDTO userDTO) {
        return accountService.createUser(userDTO);
    }


}