package com.eddiejrojas.SXMproject.reactions;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.IdClass;

@Embeddable
public class ContentReactionKey implements Serializable {
    Long userId;
    Long contentId;

    public ContentReactionKey() {
    };

    public ContentReactionKey(Long userId, Long contentId) {
        this.contentId = contentId;
        this.userId = userId;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ContentReactionKey))
            return false;
        ContentReactionKey that = (ContentReactionKey) o;
        return Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getContentId(), that.getContentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getContentId());
    }
}
