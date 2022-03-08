package com.eddiejrojas.SXMproject.users.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.eddiejrojas.SXMproject.users.exceptions.UserNotFoundException;
import com.eddiejrojas.SXMproject.users.models.UserProfile;
import com.eddiejrojas.SXMproject.users.services.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    UserProfile one(@PathVariable Long id) {
        return UserProfile.from(
                repository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException(id)));
    }

    @GetMapping("/")
    List<UserProfile> all() {
        //TODO there is repetition here and a need to maintain this standard of using profiles as opposed to users. Consider alternatives.
        return UserProfile.from(repository.findAll());
    }
}
