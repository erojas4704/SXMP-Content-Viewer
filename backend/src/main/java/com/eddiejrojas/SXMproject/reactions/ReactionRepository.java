package com.eddiejrojas.SXMproject.reactions;

import java.util.List;

import com.eddiejrojas.SXMproject.users.models.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, ContentReactionKey> {
    List<Reaction> findAllByUserId(Long userId);
}
