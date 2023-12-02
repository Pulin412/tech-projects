package com.tech.bb.moviesservice.repository;

import com.tech.bb.moviesservice.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByTitle(String title);
    List<Movie> findAllByOrderByAverageRatingDesc();

}
