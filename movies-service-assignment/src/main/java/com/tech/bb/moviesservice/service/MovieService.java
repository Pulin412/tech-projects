package com.tech.bb.moviesservice.service;

import com.tech.bb.moviesservice.entity.Movie;
import com.tech.bb.moviesservice.model.MovieServiceRequestDTO;
import com.tech.bb.moviesservice.model.TopRatedMoviesResponseDTO;

import java.util.List;

public interface MovieService {
    boolean isBestPictureWinner(MovieServiceRequestDTO movieServiceRequestDTO);
    Movie rateMovie(MovieServiceRequestDTO movieServiceRequestDTO);
    List<TopRatedMoviesResponseDTO> getTopRatedMovies();
}
