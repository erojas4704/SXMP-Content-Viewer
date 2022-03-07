package com.eddiejrojas.SXMproject.reactions;

import com.eddiejrojas.SXMproject.content.Content;
import com.eddiejrojas.SXMproject.users.User;
import jakarta.persistence.*;


@Entity
public class Reaction {
    //@EmbeddedId
    //ContentReactionKey id;
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="content_id")
    Content content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id")
    User user;

    //TODO constrain this so it's no more than 1 and no less than -1.
    @Column
    int rating;

    public Reaction(){}
    public Reaction(User user, Content content, int rating){
        this.user = user;
        this.content = content;
        this.setRating(rating);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public User getUser(){
        return this.user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public Content getContent(){
        return this.content;
    }

    public void setContent(Content content){
        this.content = content;
    }

    public int getRating(){
        return this.rating;
    }

    public void setRating(int rating) //throws RuntimeException{
    {
        if(rating > 1 || rating < - 1) throw new RuntimeException("Invalid rating. Ratings should be between -1 and 1, inclusive.");
        this.rating = rating;
    }

    public String toString(){
        return String.format("{Reaction from user: %s score: %d}", this.user, this.rating);
    }
}
