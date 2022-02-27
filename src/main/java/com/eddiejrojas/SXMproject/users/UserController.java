package com.eddiejrojas.SXMproject.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    private final UserRepository repository;
    private final UserModelAssembler assembler;

    @Autowired
    private PasswordEncoder passwordEncoder;

    UserController(UserRepository repository, UserModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @GetMapping("/users")
    List<User> all() {
        return repository.findAll();
    }

    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody User registerUser) {
        //TODO appropriate error handling with detailed feedback for redundant users
        String encodedPassword = passwordEncoder.encode(registerUser.getPassword());
        User newUser = new User(registerUser.getUsername(), registerUser.getEmail(), encodedPassword);

        EntityModel<User> entityModel = assembler.toModel(repository.save(newUser));

        return ResponseEntity.created(URI.create(""))
                .body(entityModel);
    }
}
