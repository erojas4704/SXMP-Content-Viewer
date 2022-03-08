package com.eddiejrojas.SXMproject.reactions;


import java.io.Serializable;
import java.util.Objects;

import com.eddiejrojas.SXMproject.users.models.User;

import jakarta.persistence.Embeddable;

@Embeddable
public class ContentReactionKey implements Serializable {
    Long user;
    Long content;

    public ContentReactionKey() {
    };

    public ContentReactionKey(Long userId, Long contentId) {
        this.content = contentId;
        this.user = userId;
    }

    public Long getContent() {
        return content;
    }

    public void setContent(Long content) {
        this.content = content;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ContentReactionKey))
            return false;
        ContentReactionKey that = (ContentReactionKey) o;
        return Objects.equals(getUser(), that.getUser()) && Objects.equals(getContent(), that.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getContent());
    }
}
