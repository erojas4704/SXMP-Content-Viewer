package com.eddiejrojas.sxmproject.service;

import com.eddiejrojas.sxmproject.dto.UserDTO;
import com.eddiejrojas.sxmproject.model.User;
import com.eddiejrojas.sxmproject.repository.UserRepository;
import com.eddiejrojas.sxmproject.service.exception.UserNotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    /**
     * Loads an user by their e-mail, which are used as usernames in our application.
     *
     * @param username For our system, our usernames are e-mails.
     * @return User The user that was found.
     * @throws UsernameNotFoundException if no user was found with that e=mail.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
        return user;
    }

    public UserDTO one(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return userToDTO(user);
    }

    public List<UserDTO> all() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserService::userToDTO).toList();
    }

    public static UserDTO userToDTO(User user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .avatarURL(user.getAvatarURL())
                .build();
    }
}
