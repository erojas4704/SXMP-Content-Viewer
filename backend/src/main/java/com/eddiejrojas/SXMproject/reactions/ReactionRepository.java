package com.eddiejrojas.SXMproject.reactions;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, ContentReactionKey> {
}
