package com.eddiejrojas.sxmproject.controller;

import com.eddiejrojas.sxmproject.dto.ContentDTO;
import com.eddiejrojas.sxmproject.dto.UserContentDTO;
import com.eddiejrojas.sxmproject.model.Content;
import com.eddiejrojas.sxmproject.model.User;
import com.eddiejrojas.sxmproject.service.ContentService;
import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content")
@AllArgsConstructor
public class ContentController {
    ContentService contentService;

    @GetMapping("")
    public List<UserContentDTO> all(User user) {
        return contentService.findAllContent(user.getId());
    }

    @GetMapping("/{id}")
    public UserContentDTO one(User user, @PathVariable Long id) {
        return contentService.one(user, id);
    }

    @PostMapping("")
    UserContentDTO newContent(@RequestBody Content newContent) {
        return contentService.createContent(newContent);
    }

    @PutMapping("/{id}")
    ContentDTO replaceContent(@RequestBody Content newContent, @PathVariable Long id) {
        return contentService.replaceContent(newContent, id);
    }

    @PutMapping("/{id}/like")
    UserContentDTO likeContent(User user, @PathVariable Long id) {
        return contentService.userReactsToContent(user.getId(), id, 1);
    }

    @PutMapping("/{id}/dislike")
    UserContentDTO dislikeContent(User user, @PathVariable Long id) {
        return contentService.userReactsToContent(user.getId(), id, -1);
    }

    @PutMapping("/{id}/unlike")
    UserContentDTO unlikeContent(User user, @PathVariable Long id) {
        return contentService.userReactsToContent(user.getId(), id, 0);
    }

    @PutMapping("/{id}/favorite")
    UserContentDTO favoriteContent(User user, @PathVariable Long id) {
        return contentService.userFavoritesContent(user.getId(), id, true);
    }

    // TODO lots of duplicate code here. You can probably use some param trickery to condense these
    // routes.
    @PutMapping("/{id}/unfavorite")
    UserContentDTO unfavoriteContent(User user, @PathVariable Long id) {
        return contentService.userFavoritesContent(user.getId(), id, false);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteContent(@PathVariable Long id) {
        contentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    List<UserContentDTO> search(User user, @RequestParam String term) {
        try {
            return contentService.searchContent(user, term);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
