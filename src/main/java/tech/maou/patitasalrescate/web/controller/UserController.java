package tech.maou.patitasalrescate.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tech.maou.patitasalrescate.exception.UserNotFoundException;
import tech.maou.patitasalrescate.persistence.entity.DTO.UserDTO;
import tech.maou.patitasalrescate.persistence.entity.DTO.UserRegistrationDTO;
import tech.maou.patitasalrescate.service.user.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        this.userService.save(userRegistrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<UserDTO> find() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDTO userDTO = userService.findByUsername(user.getUsername());
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Void> exist(@PathVariable String email) {
        try {
            this.userService.findByEmail(email);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
