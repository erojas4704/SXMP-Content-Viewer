package com.eddiejrojas.sxmproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "content",
        uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Content {
    @Id private Long id;
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    private String name;
    private String source;
    private String audioUrl;
    private String imageUrl;

    @Override
    public String toString() {
        return String.format("%s: %s", this.title, this.description);
    }
}
