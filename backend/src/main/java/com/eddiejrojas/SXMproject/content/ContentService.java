package com.eddiejrojas.SXMproject.content;

import com.eddiejrojas.SXMproject.reactions.Reaction;
import com.eddiejrojas.SXMproject.users.models.User;
import com.eddiejrojas.SXMproject.users.services.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ContentService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContentRepository contentRepository;

    Reaction userReactsToContent(String username, Long contentId, int rating) throws ContentNotFoundException, UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow( () -> new UsernameNotFoundException("User not found"));
        Content content = contentRepository.findById(contentId).orElseThrow( () -> new ContentNotFoundException(contentId));

        Reaction reaction = new Reaction();
        reaction.setContent(content);
        reaction.setUser(user);

        user.getReactions().add(reaction);
        userRepository.save(user);

        //TODO reactionRepository might be unnecessary if we can just use the user or content repos to save our association.
        return reaction;
    }
}
