package com.tech.bb.moviesservice.repository;

import com.tech.bb.moviesservice.entity.Movie;
import com.tech.bb.moviesservice.entity.UserMovieRating;
import com.tech.bb.moviesservice.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserMovieRatingRepository extends JpaRepository<UserMovieRating, Long> {
    Optional<UserMovieRating> findByMovieAndUser(Movie movie, Users user);
    @Query("SELECT AVG(ur.rating) FROM UserMovieRating ur WHERE ur.movie.id = :movieId")
    Double findAverageRatingByMovie(@Param("movieId") Long movieId);
}