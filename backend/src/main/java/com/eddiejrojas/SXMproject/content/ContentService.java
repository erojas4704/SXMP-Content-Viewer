package com.eddiejrojas.SXMproject.content;


import com.eddiejrojas.SXMproject.reactions.ContentReactionKey;
import com.eddiejrojas.SXMproject.reactions.Reaction;
import com.eddiejrojas.SXMproject.reactions.ReactionRepository;
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
    @Autowired
    private ReactionRepository reactionRepository;

    Reaction getOrCreateReaction(Long userId, Long contentId) {
        Reaction reaction = reactionRepository
                .findById(new ContentReactionKey(userId, contentId))
                .orElseGet(() -> {
                    System.out.println("Creating new reaction");
                    return new Reaction();
                });

        return reaction;
    }

    Reaction userReactsToContent(String username, Long contentId, int rating)
            throws ContentNotFoundException, UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Long userId = user.getId();
        Reaction reaction = getOrCreateReaction(userId, contentId);

        reaction.setContent(contentId);
        reaction.setUser(userId);
        reaction.setRating(rating);
        reactionRepository.save(reaction);

        return reaction;
    }

    Reaction userFavoritesContent(String username, Long contentId, Boolean favorite) {
        // TODO just grab the Id and take it in the param to make these 2 a little
        // cleaner.
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Long userId = user.getId();
        Reaction reaction = getOrCreateReaction(userId, contentId);
        reaction.setIsFavorite(favorite);
        reactionRepository.save(reaction);

        return reaction;
    }

}
