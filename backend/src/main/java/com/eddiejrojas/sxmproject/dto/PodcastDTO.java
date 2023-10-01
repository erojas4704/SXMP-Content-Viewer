package com.eddiejrojas.sxmproject.dto;

import java.util.Map;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.eddiejrojas.sxmproject.model.Content;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PodcastDTO {
    private List<Podcast> podcasts;

    @SuppressWarnings("unchecked")
    @JsonProperty("data")
    private void unpackNested(Map<String, Object> data) {
        Map<String, Object> podcastResponse = (Map<String, Object>) data.get("podcasts");
        List<Object> rawPodcasts = (List<Object>) podcastResponse.get("data");
        ObjectMapper om = new ObjectMapper();
        this.podcasts = om.convertValue(rawPodcasts, new TypeReference<List<Podcast>>() {
        });
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Podcast {
        private String title;
        private String description;

        @JsonIgnore
        private List<Content> episodes;

        @JsonProperty("episodes")
        private void unpackEpisodes(Map<String, Object> data) {
            ObjectMapper om = new ObjectMapper();
            this.episodes = om.convertValue(data.get("data"), new TypeReference<List<Content>>() {
            });

            // Set the title and description of each episode to the podcast title and
            // description
            this.episodes.stream()
                    .filter(episode -> {
                        // Filter out episodes that don't have an audioUrl
                        return episode.getAudioUrl() != null;
                    })
                    .forEach(episode -> {
                        episode.setTitle(this.title);
                        episode.setDescription(this.description);
                    });
        }
    }
}
