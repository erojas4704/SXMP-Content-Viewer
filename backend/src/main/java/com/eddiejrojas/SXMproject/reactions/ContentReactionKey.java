package com.eddiejrojas.SXMproject.reactions;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ContentReactionKey implements Serializable {
    @Column(name = "user_id")
    Long userId;
    @Column(name = "content_id")
    Long contentId;

    public ContentReactionKey () {};
    public ContentReactionKey (Long userId, Long contentId) {
        this.contentId = contentId;
        this.userId = userId;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long value) {
        this.contentId = value;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long value) {
        this.userId = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContentReactionKey)) return false;
        ContentReactionKey that = (ContentReactionKey) o;
        return Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getContentId(), that.getContentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getContentId());
    }
}
