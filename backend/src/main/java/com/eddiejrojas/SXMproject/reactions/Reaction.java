package com.eddiejrojas.SXMproject.reactions;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name = "reactions")
@IdClass(ContentReactionKey.class)
public class Reaction implements Serializable {

    @Id
    Long contentId;

    @Id
    Long userId;

    @Column
    int rating;

    @Column
    Boolean isFavorite = false;

    public Reaction() {
    }

    public Reaction(Long user, Long content, int rating) {
        this.userId = user;
        this.contentId = content;
        this.setRating(rating);
    }

    public Long getUser() {
        return this.userId;
    }

    public void setUser(Long user) {
        this.userId = user;
    }

    public Long getContent() {
        return this.contentId;
    }

    public void setContent(Long content) {
        this.contentId = content;
    }

    public int getRating() {
        return this.rating;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
    
    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setRating(int rating) // throws RuntimeException{
    {
        if (rating > 1 || rating < -1)
            throw new RuntimeException("Invalid rating. Ratings should be between -1 and 1, inclusive.");
        this.rating = rating;
    }

    public String toString() {
        return String.format("{Reaction from user: %s score: %d}", this.userId, this.rating);
    }
}
