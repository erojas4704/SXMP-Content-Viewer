package com.eddiejrojas.sxmproject.model;

import java.io.Serializable;

import com.eddiejrojas.sxmproject.reactions.ContentReactionKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reactions")
@IdClass(ContentReactionKey.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reaction implements Serializable {
    @Id
    Long contentId;
    @Id
    Long userId;
    @Column
    int rating;
    @Column
    Boolean isFavorite = false;

    public void setRating(int rating) throws RuntimeException {
        if (rating > 1 || rating < -1) {
            throw new RuntimeException("Invalid rating. Ratings should be between -1 and 1, inclusive.");
        }
        this.rating = rating;
    }

    public String toString() {
        return String.format("{Reaction from user: %s score: %d}", this.userId, this.rating);
    }
}
