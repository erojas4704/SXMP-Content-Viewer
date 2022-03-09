package com.eddiejrojas.SXMproject.reactions;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, ContentReactionKey> {
    List<Reaction> findAllByUserId(Long userId);

    List<Reaction> findAllByContentId(Long contentId);

    Reaction findByUserIdAndContentId(Long userId, Long contentId);
}
