package com.eddiejrojas.sxmproject.controller;

import com.eddiejrojas.sxmproject.dto.UserDTO;
import com.eddiejrojas.sxmproject.service.UserService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    UserDTO one(@PathVariable Long id) {
        return userService.one(id);
    }

    @GetMapping("/")
    List<UserDTO> all() {
        return userService.all();
    }
}
