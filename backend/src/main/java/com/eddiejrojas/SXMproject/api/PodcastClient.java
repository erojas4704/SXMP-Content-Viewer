package com.eddiejrojas.SXMproject.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class PodcastClient {
    private final String url;

    public PodcastClient(@Value("https://api.podchaser.com/graphql") String url) {
        this.url = url;
    }

    public PodcastTransfer searchPodcasts(String searchTerm, String apiKey) throws IOException {
        WebClient webClient = WebClient
                .builder()
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();

        GraphqlRequestBody graphQLRequestBody = new GraphqlRequestBody();
        String query = GraphqlSchemaReaderUtil.getSchemaFromFileName("getPodcasts");
        String variables = GraphqlSchemaReaderUtil.getSchemaFromFileName("variables");

        graphQLRequestBody.setQuery(query);
        graphQLRequestBody.setVariables(variables.replace("term", searchTerm));

        return webClient.post()
                .uri(url)
                .bodyValue(graphQLRequestBody)
                .retrieve()
                .onStatus(
                        HttpStatus.INTERNAL_SERVER_ERROR::equals,
                        response -> Mono.error(new IOException("There was an error " + response.statusCode().value())))
                .bodyToMono(PodcastTransfer.class)
                .block();
    }
}
