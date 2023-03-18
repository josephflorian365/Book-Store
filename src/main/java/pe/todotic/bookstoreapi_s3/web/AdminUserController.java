package pe.todotic.bookstoreapi_s3.web;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pe.todotic.bookstoreapi_s3.model.User;
import pe.todotic.bookstoreapi_s3.repository.UserRepository;
import pe.todotic.bookstoreapi_s3.web.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private Validator validator;
//
//    @Autowired
//    private UniqueEmailValidator uniqueEmailValidator;




    /**
     * Devuelve la lista de usuarios de forma paginada.
     * El cliente puede enviar los parámetros page, size, sort,... en la URL
     * para configurar la página solicitada.
     * Si el cliente no envía ningún parámetro para la paginación,
     * se toma la configuración por defecto.
     * Retorna el status OK: 200
     * Ej.: GET http://localhost:9090/api/admin/users?page=0&size=2&sort=nombreCompleto,desc
     *
     * @param pageable la configuración de paginación que captura los parámetros como: page, size y sort
     */
    @GetMapping
    Page<User> paginate(@PageableDefault(sort = "fullName", size = 10) Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    /**
     * Devuelve la lista completa de usuarios
     * Retorna el status OK: 200
     * Ej.: GET http://localhost:9090/api/admin/users
     */
    @GetMapping("/list")
    List<User> list() {
        return userRepository.findAll();
    }

    /**
     * Devuelve un usuario por su ID.
     * Retorna el status OK: 200
     * Ej.: GET http://localhost:9090/api/admin/users/1
     */
    @GetMapping("/{id}")
    User get(@PathVariable Integer id) {
        return userRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Crea un usuario a partir del cuerpo
     * de la solicitud HTTP y retorna
     * el usuario creado.
     * Retorna el status CREATED: 201
     * Ej.: POST http://localhost:9090/api/admin/users
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    User create( @RequestBody @Validated UserDTO userDTO) {
        List<String> errors = new ArrayList<>();
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            errors.add("El correo electrónico ya está en uso por otro usuario");
        }
        if (errors.size() > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errors.toString());
        }
        User user = modelMapper.map(userDTO, User.class);
        userRepository.save(user);
        return user;
    }

    /**
     * Actualiza un usuario por su ID, a partir
     * del cuerpo de la solicitud HTTP.
     * Retorna el status OK: 200.
     * Ej.: PUT http://localhost:9090/api/admin/users/1
     */

    @PutMapping("/{id}")
    User update(
            @PathVariable Integer id,
            @Validated @RequestBody UserDTO userDTO

    ) {
        int count = userRepository.countByEmailAndNotId(userDTO.getEmail(), id);
        if (count > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El correo electrónico ya está en uso por otro usuario");
        }
        User user = userRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
        modelMapper.map(userDTO, user);
        return userRepository.save(user);
    }

//    @PutMapping("/{id}")
//    public void updateUser(@PathVariable("id") Integer id,@Validated @RequestBody UserDTO userDto) throws NoSuchFieldException {
//        // Obtener el usuario existente
////        User existingUser = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
////        if (existingUser == null) {
////            return ResponseEntity.notFound().build();
////        }
//
//        // Establecer el ID del usuario en el DTO
////        System.out.println(existingUser.getId());
////        userDto.setUserId(existingUser.getId());
//
//        // Validar el DTO del usuario
////        Set<ConstraintViolation<UserDTO>> violations =
////                Validation.buildDefaultValidatorFactory().getValidator().validate(userDto);
//
//        // Si hay violaciones, devolver un error de validación
////        if (!violations.isEmpty()) {
////            return ResponseEntity.badRequest().body(violations);
////        }
//
//        // Si no hay violaciones, actualizar el usuario y devolver una respuesta exitosa
////        User updatedUser = userService.updateUser(id, userDto);
////        return ResponseEntity.ok(updatedUser);
//
//    }

    /**
     * Elimina un usuario por su ID.
     * Retorna el status NO_CONTENT: 204
     * Ej.: DELETE http://localhost:9090/api/admin/users/1
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);

        userRepository.delete(user);
    }

}
