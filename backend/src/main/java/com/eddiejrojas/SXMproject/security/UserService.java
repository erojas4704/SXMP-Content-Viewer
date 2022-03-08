package com.eddiejrojas.SXMproject.security;

import com.eddiejrojas.SXMproject.users.models.User;
import com.eddiejrojas.SXMproject.users.services.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Loads an user by their e-mail, which are used as usernames in our application.
     * @param username For our system, our usernames are e-mails.
     * @return User The user that was found.
     * @throws UsernameNotFoundException if no user was found with that e=mail.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
        return user;
    }
}
