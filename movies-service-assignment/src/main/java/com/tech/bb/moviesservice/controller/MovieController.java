package com.tech.bb.moviesservice.controller;

import com.tech.bb.moviesservice.config.AuthenticationFilter;
import com.tech.bb.moviesservice.entity.Movie;
import com.tech.bb.moviesservice.entity.Users;
import com.tech.bb.moviesservice.exception.UserAuthenticationException;
import com.tech.bb.moviesservice.model.MovieServiceRequestDTO;
import com.tech.bb.moviesservice.model.MovieServiceResponseDTO;
import com.tech.bb.moviesservice.model.TopRatedMovieResponseWrapper;
import com.tech.bb.moviesservice.service.MovieService;
import com.tech.bb.moviesservice.utils.MovieServiceConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/best-picture")
    public ResponseEntity<MovieServiceResponseDTO> isBestPictureWinner(@RequestParam String title, @RequestHeader String apiKey){

        // Retrieve the user from the AuthenticationFilter
        Optional<Users> optionalUser = AuthenticationFilter.getSecurityContext();

        MovieServiceRequestDTO movieServiceRequestDTO = optionalUser.map(user ->
                        MovieServiceRequestDTO.builder()
                                .title(title)
                                .user(user)
                                .build())
                .orElseThrow(() -> new UserAuthenticationException(MovieServiceConstants.EXCEPTION_INVALID_USER));

        boolean hasWon = movieService.isBestPictureWinner(movieServiceRequestDTO);

        if(hasWon)
            return new ResponseEntity<>(MovieServiceResponseDTO.builder()
                    .response(String.format("%s has won the Best Picture Award", title))
                    .build()
                    , HttpStatus.OK);
        else
            return new ResponseEntity<>(MovieServiceResponseDTO.builder()
                    .response(String.format("%s has not won the Best Picture Award", title))
                    .build()
                    , HttpStatus.OK);
    }

    @PostMapping("/rate")
    public ResponseEntity<MovieServiceResponseDTO> rateMovie(@RequestBody MovieServiceRequestDTO movieServiceRequestDTO, @RequestHeader String apiKey){
        Optional<Users> optionalUser = AuthenticationFilter.getSecurityContext();

        movieServiceRequestDTO.setUser(optionalUser.orElseThrow(() ->
                new UserAuthenticationException(MovieServiceConstants.EXCEPTION_INVALID_USER)));

        Movie response = movieService.rateMovie(movieServiceRequestDTO);
        return new ResponseEntity<>(MovieServiceResponseDTO.builder()
                .response(String.format("Rating updated for %s : %s", movieServiceRequestDTO.getTitle() , response.getAverageRating()))
                .build()
                , HttpStatus.CREATED);
    }

    @GetMapping(value = "/top-rated")
    public ResponseEntity<TopRatedMovieResponseWrapper> getTopRatedMovies(@RequestHeader String apiKey){
        Optional<Users> optionalUser = AuthenticationFilter.getSecurityContext();

        return new ResponseEntity<>(TopRatedMovieResponseWrapper.builder()
                .topRatedMoviesResponseDTOList(
                        optionalUser.map(user -> movieService.getTopRatedMovies())
                        .orElseThrow(() -> new UserAuthenticationException(MovieServiceConstants.EXCEPTION_INVALID_USER)))
                .build(), HttpStatus.OK);

    }
}
