package com.eddiejrojas.sxmproject.dto;

import com.eddiejrojas.sxmproject.model.Content;
import com.eddiejrojas.sxmproject.model.Reaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

//TODO consider making this into a simple DTO
/**
 * Subclass of content. This class contains an user's personal preferences
 * regarding a specific piece of content as well as other useful metadata that doesn't need to be persisted.
 */
@NoArgsConstructor
@Getter
@Setter
public class UserContentDTO extends Content {
    private int rating;
    private Boolean isFavorite = false;
    private int likes;
    private int dislikes;


    public UserContentDTO(Content content){
        BeanUtils.copyProperties(content, this);
    }

    public UserContentDTO(Content content, Reaction reaction) {
        BeanUtils.copyProperties(content, this);
        if (reaction != null) {
            this.rating = reaction.getRating();
            this.isFavorite = reaction.getIsFavorite();
        }
    }
}
