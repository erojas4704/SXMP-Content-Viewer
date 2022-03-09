package com.eddiejrojas.SXMproject.content;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    /**
     * Finds all the content in the database.
     * The content is then merged with the user's reactions, if any.
     * 
     * @param user The user who is requesting the content.
     * @return A list of all the content in the database.
     */
    public List<UserContent> findAllContent(User user) {
        List<Content> content = contentRepository.findAll();

        if (user == null) {
            content.stream()
                    .map(c -> (UserContent) c)
                    .collect(Collectors.toList());
        }

        // TODO pagination support.
        Map<Long, Reaction> reactions = reactionRepository.findAllByUserId(user.getId()).stream().collect(
                Collectors.toMap(Reaction::getContent, Function.identity()));

        return content.stream()
                .map(c -> {
                    Reaction reaction = reactions.get(c.getId());
                    UserContent userContent = new UserContent(c, reaction);
                    return userContent;
                })
                .collect(Collectors.toList());
    }

    private Reaction getOrCreateReaction(Long userId, Long contentId) {
        Reaction reaction = reactionRepository
                .findById(new ContentReactionKey(userId, contentId))
                .orElseGet(() -> {
                    System.out.println("Creating new reaction");
                    return new Reaction();
                });

        return reaction;
    }

    public Reaction userReactsToContent(String username, Long contentId, int rating)
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

    public Reaction userFavoritesContent(String username, Long contentId, Boolean favorite) {
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
