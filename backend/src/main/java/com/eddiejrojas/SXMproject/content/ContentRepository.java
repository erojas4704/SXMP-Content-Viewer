package com.eddiejrojas.SXMproject.content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface ContentRepository extends JpaRepository<Content, Long>{
    //TODO clean up these hacks. These sort of things can easily be done with joining annotation.
    @Query(value = "SELECT COUNT(*) FROM reactions WHERE content_id = ?1 AND rating > 0", nativeQuery = true)
    int getLikesForContent(Long id);
    @Query(value = "SELECT COUNT(*) FROM reactions WHERE content_id = ?1 AND rating < 0", nativeQuery = true)
    int getDislikesForContent(Long id);
}