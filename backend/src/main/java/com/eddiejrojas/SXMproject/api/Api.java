package com.eddiejrojas.SXMproject.api;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is a service that handles all the logic for calling the external
 * Podcast/Music provider service.
 */
@Slf4j
@Service
public class Api {
    @Value("${api.client.id}")
    private String apiId;
    @Value("${api.client.secret}")
    private String apiSecret;
    @Value("${api.url}")
    private String apiUrl;

    private String accessToken;

    @Autowired
    private PodcastClient podcastClient;

    Api() {
    }

    public String searchPodcasts(String searchTerm) throws IOException {
        PodcastTransfer pto = podcastClient.searchPodcasts(searchTerm, accessToken);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        log.info(ow.writeValueAsString(pto));
        return "";
    }

    @PostConstruct
    private void generateApiKey() throws IOException {
        WebClient client = WebClient.builder().baseUrl(apiUrl).build();

        String query = GraphqlSchemaReaderUtil.getSchemaFromFileName("authenticate");
        String variables = GraphqlSchemaReaderUtil.getSchemaFromFileName("variables");

        GraphqlRequestBody graphQLRequestBody = new GraphqlRequestBody();
        graphQLRequestBody.setQuery(query);
        graphQLRequestBody.setVariables(variables.replace("id", apiId).replace("secret", apiSecret));

        try {
            AuthDataTransferObject data = client.post()
                    .uri("")
                    .bodyValue(graphQLRequestBody)
                    .retrieve()
                    .bodyToMono(AuthDataTransferObject.class)
                    .block();

            this.accessToken = data.getAccessToken();
        } catch (WebClientResponseException err) {
            log.info(err.getMessage());
            throw new ServiceException(err.getMessage() + err.getRawStatusCode());
        }
    }
}
