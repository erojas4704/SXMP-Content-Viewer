package com.eddiejrojas.SXMproject.content;

/**
 * Subclass of content. This class contains an user's personal preferences regarding 
 * a specific piece of content.
 */
public class UserContent extends Content {
    private int rating;
    private Boolean isFavorite;

    public UserContent() {
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
