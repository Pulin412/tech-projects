package com.tech.bb.moviesservice.gateway;

import com.tech.bb.moviesservice.config.OmdbApiGatewayConfig;
import com.tech.bb.moviesservice.exception.GenericException;
import com.tech.bb.moviesservice.model.ExternalMovieGatewayDTO;
import com.tech.bb.moviesservice.model.ExternalMovieGatewayRequestDTO;
import com.tech.bb.moviesservice.utils.MovieServiceConstants;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OmdbApiMovieGatewayServiceImpl implements MovieGatewayService {

    private final OmdbApiGatewayConfig omdbApiGatewayConfig;

    public OmdbApiMovieGatewayServiceImpl(OmdbApiGatewayConfig omdbApiGatewayConfig) {
        this.omdbApiGatewayConfig = omdbApiGatewayConfig;
    }

    @Override
    public ExternalMovieGatewayDTO getMovieData(ExternalMovieGatewayRequestDTO externalMovieApiRequest) {
        String url = String.format("%s?t=%s&apikey=%s", omdbApiGatewayConfig.getOmdbUrl(), externalMovieApiRequest.getTitle(), omdbApiGatewayConfig.getApiKey());
        Mono<ExternalMovieGatewayDTO> externalMovieGatewayDTOMono =
                omdbApiGatewayConfig.getWebClient().get()
                        .uri(url)
                        .retrieve()
                        .bodyToMono(ExternalMovieGatewayDTO.class).
                        filter(movieDetails -> MovieServiceConstants.OMDB_RESPONSE_VALID_STATUS.equalsIgnoreCase(movieDetails.getResponse()))
                        .switchIfEmpty(Mono.error(new GenericException(MovieServiceConstants.EXCEPTION_OMDB_API)));

        return externalMovieGatewayDTOMono.block();
    }
}
