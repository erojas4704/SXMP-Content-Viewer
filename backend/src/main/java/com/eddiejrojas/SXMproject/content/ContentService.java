package com.eddiejrojas.SXMproject.content;

import com.eddiejrojas.SXMproject.reactions.Reaction;
import com.eddiejrojas.SXMproject.reactions.ReactionRepository;
import com.eddiejrojas.SXMproject.users.User;
import com.eddiejrojas.SXMproject.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ContentService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private ReactionRepository reactionRepository;

    Reaction userReactsToContent(String username, Long contentId, int rating) throws ContentNotFoundException, UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow( () -> new UsernameNotFoundException("User not found"));
        Content content = contentRepository.findById(contentId).orElseThrow( () -> new ContentNotFoundException(contentId));
        Reaction reaction = new Reaction(user, content, rating);
        System.out.println(content.getReactions());
        //reactionRepository.save(reaction);
        System.out.println(reaction);
        content.getReactions().add(reaction);
        //userRepository.save(user);
        contentRepository.save(content);

        //TODO reactionRepository might be unnecessary if we can just use the user or content repos to save our association.
        return reaction;
    }
}
