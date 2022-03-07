package com.eddiejrojas.SXMproject.reactions;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ContentReactionKey implements Serializable {
    @Column(name = "user_id")
    Long userid;
    @Column(name = "content_id")
    Long contentid;

    public ContentReactionKey () {};
    public ContentReactionKey (Long userId, Long contentId) {
        this.contentid = contentId;
        this.userid = userId;
    }

    public Long getContentid() {
        return contentid;
    }

    public void setContentid(Long value) {
        this.contentid = value;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long value) {
        this.userid = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContentReactionKey)) return false;
        ContentReactionKey that = (ContentReactionKey) o;
        return Objects.equals(getUserid(), that.getUserid()) && Objects.equals(getContentid(), that.getContentid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserid(), getContentid());
    }
}
