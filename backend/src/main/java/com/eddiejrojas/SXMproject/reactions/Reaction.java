package com.eddiejrojas.SXMproject.reactions;

import java.io.Serializable;

import com.eddiejrojas.SXMproject.users.models.User;

import jakarta.persistence.*;

@Entity
@Table(name = "reactions")
public class Reaction implements Serializable{
    // @EmbeddedId
    // ContentReactionKey id;
    // @Id
    // @GeneratedValue
    // Long id;

    @EmbeddedId
    Long contentId;

    @EmbeddedId
    Long userId;

    // TODO constrain this so it's no more than 1 and no less than -1.

    @Column
    int rating;

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
