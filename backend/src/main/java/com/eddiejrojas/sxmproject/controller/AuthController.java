package com.eddiejrojas.sxmproject.controller;

import com.eddiejrojas.sxmproject.dto.AuthorizationDTO;
import com.eddiejrojas.sxmproject.dto.LoginCredentialsDTO;
import com.eddiejrojas.sxmproject.model.User;
import com.eddiejrojas.sxmproject.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private AuthService authService;

    @PostMapping("/register")
    AuthorizationDTO register(@RequestBody User registerUser) {
        return authService.register(registerUser);
    }

    @PostMapping("/login")
    AuthorizationDTO login(@RequestBody LoginCredentialsDTO loginCredentialsDTO) {
        return authService.login(loginCredentialsDTO);
    }
}
