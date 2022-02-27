package com.eddiejrojas.SXMproject.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    UserProfile one(@PathVariable Long id) {
        return UserProfile.from(
                repository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException(id)));
    }

    @GetMapping("/users")
    List<UserProfile> all() {
        //TODO there is repetition here and a need to maintain this standard of using profiles as opposed to users. Consider alternatives.
        return UserProfile.from(repository.findAll());
    }

    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody User registerUser) {
        //TODO appropriate error handling with detailed feedback for redundant users
        //TODO delegate this to a service
        String encodedPassword = passwordEncoder.encode(registerUser.getPassword());
        User newUser = new User(registerUser.getUsername(), registerUser.getEmail(), encodedPassword);


        UserProfile profile = UserProfile.from(repository.save(newUser));
        EntityModel<UserProfile> entityModel = assembler.toModel(profile);

        return ResponseEntity.created(URI.create(""))
                .body(entityModel);
    }
}
