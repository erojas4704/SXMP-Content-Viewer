package com.eddiejrojas.sxmproject.service;

import com.eddiejrojas.sxmproject.dto.AuthorizationDTO;
import com.eddiejrojas.sxmproject.dto.LoginCredentialsDTO;
import com.eddiejrojas.sxmproject.model.User;
import com.eddiejrojas.sxmproject.repository.UserRepository;
import com.eddiejrojas.sxmproject.security.JWTUtils;
import com.eddiejrojas.sxmproject.service.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private JWTUtils jwtUtils;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    public AuthorizationDTO register(User registerUser) {
        // TODO appropriate error handling with detailed feedback for redundant users
        registerUser.setPassword(passwordEncoder.encode(registerUser.getPassword()));
        User newUser = userRepository.save(registerUser);
        String token = jwtUtils.generateToken(newUser);
        AuthorizationDTO authorizationDTO = new AuthorizationDTO();
        authorizationDTO.setToken(token);
        authorizationDTO.setUsername(newUser.getUsername());
        authorizationDTO.setExpires(jwtUtils.getExpirationDateFromToken(token));
        return authorizationDTO;
    }

    public AuthorizationDTO login(LoginCredentialsDTO loginCredentialsDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginCredentialsDTO.getUsername(), loginCredentialsDTO.getPassword()));
        User user =
                userRepository
                        .findByUsername(loginCredentialsDTO.getUsername())
                        .orElseThrow(
                                () -> new UserNotFoundException(loginCredentialsDTO.getUsername()));

        String token = jwtUtils.generateToken(user);
        AuthorizationDTO userAuth = new AuthorizationDTO();
        userAuth.setToken(token);
        userAuth.setUsername(loginCredentialsDTO.getUsername());
        userAuth.setExpires(jwtUtils.getExpirationDateFromToken(token));
        return userAuth;
    }
}
