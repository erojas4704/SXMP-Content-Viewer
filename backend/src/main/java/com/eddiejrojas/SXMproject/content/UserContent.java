package com.eddiejrojas.SXMproject.content;

import com.eddiejrojas.SXMproject.reactions.Reaction;

import org.springframework.beans.BeanUtils;

/**
 * Subclass of content. This class contains an user's personal preferences
 * regarding
 * a specific piece of content.
 */
public class UserContent extends Content {
    private int rating;
    private Boolean isFavorite = false;

    public UserContent() {
    }

    public UserContent(Content content, Reaction reaction) {
        BeanUtils.copyProperties(content, this);
        if (reaction != null) {
            this.rating = reaction.getRating();
            this.isFavorite = reaction.getIsFavorite();
        }
    }

    public void setRating(int rating) {
        this.rating = rating;
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
}
