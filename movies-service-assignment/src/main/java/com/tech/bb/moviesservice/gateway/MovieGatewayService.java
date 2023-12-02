package com.tech.bb.moviesservice.gateway;

import com.tech.bb.moviesservice.model.ExternalMovieGatewayDTO;
import com.tech.bb.moviesservice.model.ExternalMovieGatewayRequestDTO;

public interface MovieGatewayService {
    ExternalMovieGatewayDTO getMovieData(ExternalMovieGatewayRequestDTO externalMovieApiRequest);
}
