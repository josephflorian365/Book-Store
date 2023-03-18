package pe.todotic.bookstoreapi_s3.web;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.todotic.bookstoreapi_s3.model.User;
import pe.todotic.bookstoreapi_s3.repository.UserRepository;
import pe.todotic.bookstoreapi_s3.web.dto.NormalUserDTO;

@RestController
@RequestMapping("/api/normal-users")
public class NormalUserController {

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Validated @RequestBody NormalUserDTO normalUserDTO) {
        // validar si el email ya existe en la base de datos
        if (userRepository.findByEmail(normalUserDTO.getEmail()) != null) {
            return ResponseEntity.badRequest().build();
        }
        //Convierte normalUserDTO a tipo User con modelMapper
        User newUser = modelMapper.map(normalUserDTO, User.class);
        // guardar el nuevo usuario en la base de datos
        userRepository.save(newUser);

        // retornar el nuevo usuario con un código de respuesta 200
        return ResponseEntity.ok(newUser);
    }
}
