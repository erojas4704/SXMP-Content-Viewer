package com.eddiejrojas.SXMproject.users;

import com.eddiejrojas.SXMproject.security.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
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
    private final UserModelAssembler assembler;

    AuthController(UserRepository repository, UserModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody User registerUser) {
        // TODO appropriate error handling with detailed feedback for redundant users
        // TODO delegate this to a service
        registerUser.setPassword(passwordEncoder.encode(registerUser.getPassword()));
        User newUser = repository.save(registerUser);
        LoginDetails loginDetails = new LoginDetails(newUser.getUsername(), registerUser.getPassword());
        String token = jwtUtils.generateToken(loginDetails);
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

        } catch (Exception err) {
            throw new Exception("Invalid username or password");
        }

        String token = jwtUtils.generateToken(loginDetails);
        AuthorizedUser user = new AuthorizedUser();
        user.setToken(token);
        user.setUsername(loginDetails.getUsername());
        user.setExpires(jwtUtils.getExpirationDateFromToken(token));

        return ResponseEntity.ok(user);
    }
}
