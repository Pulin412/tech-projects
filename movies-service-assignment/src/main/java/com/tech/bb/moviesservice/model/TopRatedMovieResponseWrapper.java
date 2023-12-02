package com.tech.bb.moviesservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopRatedMovieResponseWrapper {
    @JsonProperty("topRatedMoviesByBoxOffice")
    private List<TopRatedMoviesResponseDTO> topRatedMoviesResponseDTOList;
}
