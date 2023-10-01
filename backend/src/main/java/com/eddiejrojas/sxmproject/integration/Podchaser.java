package com.eddiejrojas.sxmproject.integration;

import com.eddiejrojas.sxmproject.dto.PodcastDTO;
import com.eddiejrojas.sxmproject.dto.PodchaserAuthDTO;
import com.eddiejrojas.sxmproject.model.Content;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/**
 * This class is a client that handles all the logic for calling the external
 * Podcast/Music provider service.
 */
@Slf4j
@Service
public class Podchaser {
    @Value("${api.client.id}")
    private String apiId;

    @Value("${api.client.secret}")
    private String apiSecret;

    @Value("${api.url}")
    private String apiUrl;

    private PodchaserClient podchaserClient;
    private String accessToken;

    public Podchaser(PodchaserClient podchaserClient) {
        this.podchaserClient = podchaserClient;
    }

    public List<Content> searchPodcasts(String searchTerm) throws IOException {
        PodcastDTO pto = podchaserClient.searchPodcasts(searchTerm, accessToken);

        return pto.getPodcasts().stream()
                .map(o -> o.getEpisodes())
                .flatMap(x -> x.stream())
                .toList();
    }

    @PostConstruct
    private void generateApiKey() throws IOException {
        WebClient client = WebClient.builder().baseUrl(apiUrl).build();
        String query = GraphqlSchemaReaderUtil.getSchemaFromFileName("authenticate");
        String variables = GraphqlSchemaReaderUtil.getSchemaFromFileName("variables");
        GraphqlRequestBody graphQLRequestBody = new GraphqlRequestBody();
        graphQLRequestBody.setQuery(query);
        graphQLRequestBody.setVariables(
                variables.replace("id", apiId).replace("secret", apiSecret));

        try {
            PodchaserAuthDTO data =
                    client.post()
                            .uri("")
                            .bodyValue(graphQLRequestBody)
                            .retrieve()
                            .bodyToMono(PodchaserAuthDTO.class)
                            .block();

            this.accessToken = data.getAccessToken();
        } catch (WebClientResponseException err) {
            log.info(err.getMessage());
            throw new ServiceException(err.getMessage() + err.getRawStatusCode());
        }
    }
}
