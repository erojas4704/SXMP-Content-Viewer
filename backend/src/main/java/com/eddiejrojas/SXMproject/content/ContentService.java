package com.eddiejrojas.SXMproject.content;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.eddiejrojas.SXMproject.api.Api;
import com.eddiejrojas.SXMproject.reactions.ContentReactionKey;
import com.eddiejrojas.SXMproject.reactions.Reaction;
import com.eddiejrojas.SXMproject.reactions.ReactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentService {
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private ReactionRepository reactionRepository;
    @Autowired
    private Api api;

    /**
     * Makes a call to an external API to search for new content.
     * If the content does not exist in our local database, it is added.
     * Also searches the local database for content.
     * 
     * @param searchTerm
     * @return A list of content that matches the search term.
     */
    public List<Content> searchContent(Long userId, String searchTerm) throws IOException {
        List<Content> content = api.searchPodcasts(searchTerm);
        contentRepository.saveAll(content);
        return content;
    }

    /**
     * Finds content by id.
     * 
     * @param user The user making the request. Can be -1 for null.
     * @param id   The id of the content to find.
     * @return The UserContent object, that includes the user's own reaction as well
     *         as the reactions of other users.
     */
    public UserContent findContent(Long userId, Long id) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new ContentNotFoundException(id));

        // TODO repetition here. This method can be extracted and condensed.
        Reaction reaction = reactionRepository.findByUserIdAndContentId(userId, content.getId());
        UserContent userContent = new UserContent(content, reaction);
        userContent.setLikes(contentRepository.getLikesForContent(content.getId()));
        userContent.setDislikes(contentRepository.getDislikesForContent(content.getId()));
        return userContent;
    }

    /**
     * Finds all the content in the database.
     * The content is then merged with the user's reactions, if any.
     * 
     * @param user The user who is requesting the content.
     * @return A list of all the content in the database.
     */
    public List<UserContent> findAllContent(Long userId) {
        List<Content> content = contentRepository.findAll();

        if (userId == -1) {
            return content.stream()
                    .map(c -> new UserContent(c))
                    .collect(Collectors.toList());
        }

        // TODO pagination support.
        Map<Long, Reaction> reactions = reactionRepository.findAllByUserId(userId).stream().collect(
                Collectors.toMap(Reaction::getContent, Function.identity()));

        // TODO refactor. DRY
        return content.stream()
                .map(c -> {
                    Reaction reaction = reactions.get(c.getId());
                    UserContent userContent = new UserContent(c, reaction);
                    userContent.setLikes(contentRepository.getLikesForContent(c.getId()));
                    userContent.setDislikes(contentRepository.getDislikesForContent(c.getId()));
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

    public UserContent userReactsToContent(Long userId, Long contentId, int rating) {

        Reaction reaction = getOrCreateReaction(userId, contentId);

        reaction.setContent(contentId);
        reaction.setUser(userId);
        reaction.setRating(rating);
        reactionRepository.save(reaction);

        return findContent(userId, contentId);
    }

    public UserContent userFavoritesContent(Long userId, Long contentId, Boolean favorite) {

        Reaction reaction = getOrCreateReaction(userId, contentId);
        reaction.setIsFavorite(favorite);
        reactionRepository.save(reaction);

        return findContent(userId, contentId);
    }

}
