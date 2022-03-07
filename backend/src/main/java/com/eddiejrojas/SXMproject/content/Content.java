package com.eddiejrojas.SXMproject.content;

import com.eddiejrojas.SXMproject.reactions.Reaction;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "content",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id")
        })
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "text")
    private String description;
    private String name;
    private String source;
    private String audioURL;
    private String imageURL;

    @OneToMany(mappedBy="content")
    private Set<Reaction> reactions;

    Content() {}

    Content(String title, String name, String description, String source, String audioURL, String imageURL) {
        this.title = title;
        this.name = name;
        this.description = description;
        this.source = source;
        this.audioURL = audioURL;
        this.imageURL = imageURL;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String value) {
        this.source = value;
    }

    public String getAudioURL() {
        return this.audioURL;
    }

    public void setAudioURL(String value) {
        this.audioURL = value;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public void setImageURL(String value) {
        this.imageURL = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public Set<Reaction> getReactions(){
        return this.reactions;
    }

    public void setReactions(Set<Reaction> reactions){
        this.reactions = reactions;
    }


    @Override
    public String toString() {
        return String.format("%s: %s", this.title, this.description);
    }
}
