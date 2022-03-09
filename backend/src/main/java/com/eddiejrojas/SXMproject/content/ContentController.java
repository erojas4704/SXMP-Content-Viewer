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
    Content one(User user, @PathVariable Long id) {
        Content content = repository.findById(id)
                .orElseThrow(() -> new ContentNotFoundException(id));

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
    ResponseEntity<?> likeContent(Authentication authentication, Principal principal, @PathVariable Long id) {
        String username = authentication.getName();
        Reaction react = contentService.userReactsToContent(username, id, 1);
        return ResponseEntity.ok().body(react);
    }

    @PutMapping("/{id}/dislike")
    ResponseEntity<?> dislikeContent(Authentication authentication, Principal principal, @PathVariable Long id) {
        String username = authentication.getName();
        Reaction react = contentService.userReactsToContent(username, id, -1);
        return ResponseEntity.ok().body(react);
    }

    @PutMapping("/{id}/unlike")
    ResponseEntity<?> unlikeContent(Authentication authentication, Principal principal, @PathVariable Long id) {
        String username = authentication.getName();
        Reaction react = contentService.userReactsToContent(username, id, 0);
        return ResponseEntity.ok().body(react);
    }

    @PutMapping("/{id}/favorite")
    ResponseEntity<?> favoriteContent(Authentication authentication, Principal principal, @PathVariable Long id) {
        String username = authentication.getName();
        Reaction react = contentService.userFavoritesContent(username, id, true);
        return ResponseEntity.ok().body(react);
    }

    // TODO lots of duplicate code here. You can probably use some param trickery to
    // condense these routes.
    @PutMapping("/{id}/unfavorite")
    ResponseEntity<?> unfavoriteContent(Authentication authentication, Principal principal, @PathVariable Long id) {
        String username = authentication.getName();
        Reaction react = contentService.userFavoritesContent(username, id, false);
        return ResponseEntity.ok().body(react);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteContent(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
