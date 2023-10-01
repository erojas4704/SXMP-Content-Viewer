package com.eddiejrojas.sxmproject.service;

import com.eddiejrojas.sxmproject.dto.ContentDTO;
import com.eddiejrojas.sxmproject.dto.UserContentDTO;
import com.eddiejrojas.sxmproject.integration.Podchaser;
import com.eddiejrojas.sxmproject.model.Content;
import com.eddiejrojas.sxmproject.model.Reaction;
import com.eddiejrojas.sxmproject.model.User;
import com.eddiejrojas.sxmproject.reactions.ContentReactionKey;
import com.eddiejrojas.sxmproject.repository.ContentRepository;
import com.eddiejrojas.sxmproject.repository.ReactionRepository;
import com.eddiejrojas.sxmproject.service.exception.ContentNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContentService {
    private ContentRepository contentRepository;
    private ReactionRepository reactionRepository;
    private Podchaser api;

    /**
     * Makes a call to an external API to search for new content.
     * If the content does not exist in our local database, it is added.
     * Also searches the local database for content.
     *
     * @param searchTerm
     * @return A list of content that matches the search term.
     */
    public List<UserContentDTO> searchContent(Long userId, String searchTerm) throws IOException {
        List<Content> content = api.searchPodcasts(searchTerm);
        contentRepository.saveAll(content);
        // Map the content to the user's reactions, if the user exists
        return content.stream()
                .map(
                        c -> {
                            Reaction reaction =
                                    reactionRepository.findByUserIdAndContentId(userId, c.getId());
                            if (reaction == null) return new UserContentDTO(c);

                            UserContentDTO userContentDTO = new UserContentDTO(c, reaction);
                            userContentDTO.setLikes(
                                    contentRepository.getLikesForContent(c.getId()));
                            userContentDTO.setDislikes(
                                    contentRepository.getDislikesForContent(c.getId()));
                            return userContentDTO;
                        })
                .toList();
    }

    /**
     * Finds content by id.
     *
     * @param userId The user making the request. Can be -1 for null.
     * @param id     The id of the content to find.
     * @return The UserContent object, that includes the user's own reaction as well
     * as the reactions of other users.
     */
    public UserContentDTO findContent(Long userId, Long id) {
        Content content =
                contentRepository.findById(id).orElseThrow(() -> new ContentNotFoundException(id));

        // TODO repetition here. This method can be extracted and condensed.
        Reaction reaction = reactionRepository.findByUserIdAndContentId(userId, content.getId());
        UserContentDTO userContentDTO = new UserContentDTO(content, reaction);
        userContentDTO.setLikes(contentRepository.getLikesForContent(content.getId()));
        userContentDTO.setDislikes(contentRepository.getDislikesForContent(content.getId()));
        return userContentDTO;
    }

    /**
     * Finds all the content in the database.
     * The content is then merged with the user's reactions, if any.
     *
     * @param userId The user who is requesting the content. This is used for favorites, likes, and dislikes.
     * @return A list of all the content in the database.
     */
    public List<UserContentDTO> findAllContent(Long userId) {
        List<Content> content = contentRepository.findAll();

        if (userId == -1) {
            return content.stream().map(c -> new UserContentDTO(c)).collect(Collectors.toList());
        }

        // TODO pagination support.
        Map<Long, Reaction> reactions =
                reactionRepository.findAllByUserId(userId).stream()
                        .collect(Collectors.toMap(Reaction::getContentId, Function.identity()));

        // TODO refactor. DRY
        return content.stream()
                .map(
                        c -> {
                            Reaction reaction = reactions.get(c.getId());
                            UserContentDTO userContentDTO = new UserContentDTO(c, reaction);
                            userContentDTO.setLikes(
                                    contentRepository.getLikesForContent(c.getId()));
                            userContentDTO.setDislikes(
                                    contentRepository.getDislikesForContent(c.getId()));
                            return userContentDTO;
                        })
                .collect(Collectors.toList());
    }

    private Reaction getOrCreateReaction(Long userId, Long contentId) {
        Reaction reaction =
                reactionRepository
                        .findById(new ContentReactionKey(userId, contentId))
                        .orElseGet(
                                () -> {
                                    System.out.println("Creating new reaction");
                                    return new Reaction();
                                });

        return reaction;
    }

    public UserContentDTO createContent(Content newContent) {
        Long nextId = -contentRepository.getNextAvailableId();
        // Since we're dealing with locally cached external API's, our content IDs will be negative
        // for content produced in-house.
        newContent.setId(nextId);
        return new UserContentDTO(contentRepository.save(newContent));
    }

    public UserContentDTO userReactsToContent(Long userId, Long contentId, int rating) {

        Reaction reaction = getOrCreateReaction(userId, contentId);

        reaction.setContentId(contentId);
        reaction.setUserId(userId);
        reaction.setRating(rating);
        reactionRepository.save(reaction);

        return findContent(userId, contentId);
    }

    public UserContentDTO userFavoritesContent(Long userId, Long contentId, Boolean favorite) {

        Reaction reaction = getOrCreateReaction(userId, contentId);
        reaction.setIsFavorite(favorite);
        reactionRepository.save(reaction);

        return findContent(userId, contentId);
    }

    public ContentDTO replaceContent(Content newContent, Long id) {
        Content updatedContent =
                contentRepository
                        .findById(id)
                        .map(
                                content -> {
                                    content.setTitle(newContent.getTitle());
                                    content.setDescription(newContent.getDescription());
                                    content.setAudioUrl(newContent.getAudioUrl());
                                    content.setSource(newContent.getSource());
                                    content.setImageUrl(newContent.getImageUrl());
                                    return contentRepository.save(content);
                                })
                        .orElseGet(
                                () -> {
                                    newContent.setId(id);
                                    return contentRepository.save(newContent);
                                });

        return mapToDTO(updatedContent);
    }

    protected ContentDTO mapToDTO(Content content) {
        return ContentDTO.builder()
                .name(content.getName())
                .description(content.getDescription())
                .title(content.getTitle())
                .source(content.getSource())
                .audioUrl(content.getAudioUrl())
                .imageUrl(content.getImageUrl())
                .id(content.getId())
                .build();
    }

    public UserContentDTO one(User user, Long id) {
        Long userId = user == null ? -1 : user.getId();
        UserContentDTO content = findContent(userId, id);
        return content;
    }
}
