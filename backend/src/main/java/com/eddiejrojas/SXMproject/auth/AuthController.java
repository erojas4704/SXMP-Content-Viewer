package com.eddiejrojas.SXMproject.auth;

import com.eddiejrojas.SXMproject.security.JWTUtils;
import com.eddiejrojas.SXMproject.users.exceptions.UserNotFoundException;
import com.eddiejrojas.SXMproject.users.models.AuthorizedUser;
import com.eddiejrojas.SXMproject.users.models.LoginDetails;
import com.eddiejrojas.SXMproject.users.models.User;
import com.eddiejrojas.SXMproject.users.services.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTUtils jwtUtils;

    private final UserRepository repository;

    AuthController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody User registerUser) {
        // TODO appropriate error handling with detailed feedback for redundant users
        // TODO delegate this to a service
        registerUser.setPassword(passwordEncoder.encode(registerUser.getPassword()));
        User newUser = repository.save(registerUser);
        String token = jwtUtils.generateToken(newUser);
        AuthorizedUser authorizedUser = new AuthorizedUser(newUser.getUsername(), token,
                jwtUtils.getExpirationDateFromToken(token));

        return ResponseEntity.created(URI.create(""))
                .body(authorizedUser);
    }

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginDetails loginDetails) throws Exception {
        System.out.println(loginDetails);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDetails.getUsername(), loginDetails.getPassword()));
            User user = repository.findByUsername(loginDetails.getUsername())
                    .orElseThrow(() -> new UserNotFoundException(loginDetails.getUsername()));

            String token = jwtUtils.generateToken(user);
            AuthorizedUser userAuth = new AuthorizedUser();
            userAuth.setToken(token);
            userAuth.setUsername(loginDetails.getUsername());
            userAuth.setExpires(jwtUtils.getExpirationDateFromToken(token));
            return ResponseEntity.ok(userAuth);

        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
