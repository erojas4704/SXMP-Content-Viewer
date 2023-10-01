package com.eddiejrojas.sxmproject.repository;

import com.eddiejrojas.sxmproject.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContentRepository extends JpaRepository<Content, Long> {
    // TODO clean up these hacks. These sort of things can easily be done with joining annotation.
    @Query(
            value = "SELECT COUNT(*) FROM reactions WHERE content_id = ?1 AND rating > 0",
            nativeQuery = true)
    public int getLikesForContent(Long id);

    @Query(
            value = "SELECT COUNT(*) FROM reactions WHERE content_id = ?1 AND rating < 0",
            nativeQuery = true)
    public int getDislikesForContent(Long id);

    @Query(value = "SELECT nextval('users_id_seq');", nativeQuery = true)
    public Long getNextAvailableId();
}
