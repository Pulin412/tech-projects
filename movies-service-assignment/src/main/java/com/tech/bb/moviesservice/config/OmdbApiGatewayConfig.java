package com.tech.bb.moviesservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Getter
public class OmdbApiGatewayConfig {
    @Value("${movie.service.omdb.apikey}")
    private String apiKey;

    @Value("${movie.service.omdb.url}")
    private String omdbUrl;

    private final WebClient webClient;

    public OmdbApiGatewayConfig(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(omdbUrl).build();
    }
}
