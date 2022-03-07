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
        //TODO appropriate error handling with detailed feedback for redundant users
        //TODO delegate this to a service
        String encodedPassword = passwordEncoder.encode(registerUser.getPassword());
        User newUser = new User(registerUser.getUsername(), registerUser.getUsername(), encodedPassword);

        UserProfile profile = UserProfile.from(repository.save(newUser));
        EntityModel<UserProfile> entityModel = assembler.toModel(profile);



        return ResponseEntity.created(URI.create(""))
                .body(entityModel);
    }

    @PostMapping("/login")
    String login(@RequestBody LoginDetails loginDetails) throws Exception {
        System.out.println(loginDetails);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDetails.getEmail(), loginDetails.getPassword())
            );

        }catch(Exception err) {
            throw new Exception("Invalid username or password");
        }

        return jwtUtils.generateToken(loginDetails);
    }
}
