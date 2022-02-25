package com.eddiejrojas.SXMproject.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
class Content {
    private @Id @GeneratedValue Long id;
    private String title;
    private String description;
    private String source;
    private String audioURL;
    private String imageURL;

    Content() {}
    Content(String title, String description, String source, String audioURL, String imageURL){
        this.title = title;
        this.description = description;
        this.source = source;
        this.audioURL = audioURL;
        this.imageURL = imageURL;
    }

    public Long getId(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public String getSource(){
        return this.source;
    }

    public String getAudioURL(){
        return this.audioURL;
    }

    public String getImageURL(){
        return this.imageURL;
    }

}
