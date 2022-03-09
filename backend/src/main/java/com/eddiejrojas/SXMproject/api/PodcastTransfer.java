package com.eddiejrojas.SXMproject.api;

import lombok.Getter;

@Getter
public class PodcastTransfer {
    private PodcastData data;

    @Getter
    public class PodcastData {
        private Podcast podcast;

        @Getter
        public class Podcast {
            private String title;
            private String description;
            private String imageURL;
            private String audioURL;
            private String author;

        }
    }
}
