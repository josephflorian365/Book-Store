package pe.todotic.bookstoreapi_s3.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.todotic.bookstoreapi_s3.exception.BadRequestException;
import pe.todotic.bookstoreapi_s3.model.User;
import pe.todotic.bookstoreapi_s3.repository.UserRepository;
import pe.todotic.bookstoreapi_s3.web.dto.SignupUserDTO;

@Service
@AllArgsConstructor
public class AccountService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getInfo(String email) {
        return userRepository
                .findOneByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
    }

    public User createUser(SignupUserDTO userDTO) {
        boolean emailAlreadyExists = userRepository.existsByEmail(userDTO.getEmail());

        if (emailAlreadyExists) {
            throw new BadRequestException("Email already exists.");
        }
        String password = passwordEncoder.encode(userDTO.getPassword());

        User user = new ModelMapper().map(userDTO, User.class);
        user.setPassword(password);
        user.setRole(User.Role.USER);

        return userRepository.save(user);
    }

}
