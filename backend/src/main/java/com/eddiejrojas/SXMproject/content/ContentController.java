package com.eddiejrojas.SXMproject.content;

import com.eddiejrojas.SXMproject.reactions.Reaction;
import com.eddiejrojas.SXMproject.users.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/content")
class ContentController {
    private final ContentRepository repository;
    private final ContentModelAssembler assembler;
    @Autowired
    ContentService contentService;

    ContentController(ContentRepository repository, ContentModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("")
    List<UserContent> all(User user) {
        List<UserContent> content = contentService.findAllContent(user);
        return content;
    }

    @GetMapping("/{id}")
    UserContent one(User user, @PathVariable Long id) {
        UserContent content = contentService.findContent(user, id);
        return content;
    }

    @PostMapping("")
    ResponseEntity<?> newContent(@RequestBody Content newContent) {
        EntityModel<Content> entityModel = assembler.toModel(repository.save(newContent));
        return ResponseEntity
                .created(URI.create("")) // TODO reconsider our links
                .body(entityModel);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> replaceContent(@RequestBody Content newContent, @PathVariable Long id) {
        Content updatedContent = repository.findById(id)
                .map(content -> {
                    content.setTitle(newContent.getTitle());
                    content.setDescription(newContent.getDescription());
                    content.setAudioURL(newContent.getAudioURL());
                    content.setSource(newContent.getSource());
                    content.setImageURL(newContent.getImageURL());
                    return repository.save(content);
                })
                .orElseGet(() -> {
                    newContent.setId(id);
                    return repository.save(newContent);
                });

        EntityModel<Content> entityModel = assembler.toModel(updatedContent);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}/like")
    ResponseEntity<?> likeContent(User user, @PathVariable Long id) {
        Reaction react = contentService.userReactsToContent(user.getId(), id, 1);
        return ResponseEntity.ok().body(react);
    }

    @PutMapping("/{id}/dislike")
    ResponseEntity<?> dislikeContent(User user, @PathVariable Long id) {
        Reaction react = contentService.userReactsToContent(user.getId(), id, -1);
        return ResponseEntity.ok().body(react);
    }

    @PutMapping("/{id}/unlike")
    ResponseEntity<?> unlikeContent(User user, @PathVariable Long id) {
        Reaction react = contentService.userReactsToContent(user.getId(), id, 0);
        return ResponseEntity.ok().body(react);
    }

    @PutMapping("/{id}/favorite")
    ResponseEntity<?> favoriteContent(User user, @PathVariable Long id) {
        Reaction react = contentService.userFavoritesContent(user.getId(), id, true);
        return ResponseEntity.ok().body(react);
    }

    // TODO lots of duplicate code here. You can probably use some param trickery to
    // condense these routes.
    @PutMapping("/{id}/unfavorite")
    ResponseEntity<?> unfavoriteContent(User user, @PathVariable Long id) {
        Reaction react = contentService.userFavoritesContent(user.getId(), id, false);
        return ResponseEntity.ok().body(react);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteContent(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
