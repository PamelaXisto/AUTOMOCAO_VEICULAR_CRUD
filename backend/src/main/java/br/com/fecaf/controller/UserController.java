package br.com.fecaf.controller;

import br.com.fecaf.dto.request.UserDTO;
import br.com.fecaf.model.User;
import br.com.fecaf.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {

        UserDTO createdUser = userService.registerUser(userDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
