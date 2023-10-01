package com.eddiejrojas.sxmproject.reactions;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ContentReactionKey implements Serializable {
    Long userId;
    Long contentId;

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
