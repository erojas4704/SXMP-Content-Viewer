package com.eddiejrojas.sxmproject.integration;

import com.eddiejrojas.sxmproject.dto.PodcastDTO;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PodchaserClient {
    private final String url;

    public PodchaserClient(@Value("https://api.podchaser.com/graphql") String url) {
        this.url = url;
    }

    public PodcastDTO searchPodcasts(String searchTerm, String apiKey) throws IOException {
        WebClient webClient =
                WebClient.builder().defaultHeader("Authorization", "Bearer " + apiKey).build();

        GraphqlRequestBody graphQLRequestBody = new GraphqlRequestBody();
        String query = GraphqlSchemaReaderUtil.getSchemaFromFileName("getPodcasts");
        String variables = GraphqlSchemaReaderUtil.getSchemaFromFileName("variables");

        graphQLRequestBody.setQuery(query);
        graphQLRequestBody.setVariables(variables.replace("term", searchTerm));

        PodcastDTO data =
                webClient
                        .post()
                        .uri(url)
                        .bodyValue(graphQLRequestBody)
                        .retrieve()
                        .onStatus(
                                HttpStatus.INTERNAL_SERVER_ERROR::equals,
                                response ->
                                        Mono.error(
                                                new IOException(
                                                        "There was an error "
                                                                + response.statusCode().value())))
                        .bodyToMono(PodcastDTO.class)
                        .block();
        return data;
    }
}
