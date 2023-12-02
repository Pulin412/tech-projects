package com.tech.bb.moviesservice.service;

import com.tech.bb.moviesservice.model.MovieCsvDTO;

import java.util.List;

public interface FileReaderService {
    List<MovieCsvDTO> readMovieData();
}
