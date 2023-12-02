package com.tech.bb.moviesservice.unit;

import com.tech.bb.moviesservice.entity.Category;
import com.tech.bb.moviesservice.entity.Movie;
import com.tech.bb.moviesservice.entity.UserMovieRating;
import com.tech.bb.moviesservice.entity.Users;
import com.tech.bb.moviesservice.exception.GenericException;
import com.tech.bb.moviesservice.exception.MovieNotFoundException;
import com.tech.bb.moviesservice.model.MovieServiceRequestDTO;
import com.tech.bb.moviesservice.repository.MovieRepository;
import com.tech.bb.moviesservice.repository.UserMovieRatingRepository;
import com.tech.bb.moviesservice.repository.UsersRepository;
import com.tech.bb.moviesservice.service.MovieServiceImpl;
import com.tech.bb.moviesservice.utils.MovieServiceConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

public class MovieServiceTests {

    @Mock
    private MovieRepository movieRepository;
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private UserMovieRatingRepository userMovieRatingRepository;
    @InjectMocks
    private MovieServiceImpl movieService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /*
        Unit tests for isBestPictureWinner feature
     */

    @Test
    public void whenIsBestPicureWinnerAndNoTitlePassed_thenThrowException(){
        Assertions.assertThrows(MovieNotFoundException.class, () -> movieService.isBestPictureWinner(MovieServiceRequestDTO.builder().build()));
    }

    @Test
    public void whenIsBestPictureWinnerAndValidTitlePassedWithValidCategory_thenReturnTrueResponse() {
        Set<Category> categorySet = new HashSet<>();
        categorySet.add(Category.builder().category(MovieServiceConstants.CATEGORY_BEST_PICTURE).build());
        when(movieRepository.findByTitle(Mockito.any())).thenReturn(Optional.of(Movie.builder().title("Titanic").movieCategories(categorySet).won(true).build()));
        Assertions.assertTrue(movieService.isBestPictureWinner(MovieServiceRequestDTO.builder().title("Titanic").rating(1f).build()));
    }

    @Test
    public void whenIsBestPictureWinnerAndValidTitlePassedWithInvalidCategory_thenReturnFalseResponse() {
        Set<Category> categorySet = new HashSet<>();
        categorySet.add(Category.builder().category("Best Director").build());
        when(movieRepository.findByTitle(Mockito.any())).thenReturn(Optional.of(Movie.builder().title("Titanic").movieCategories(categorySet).won(true).build()));
        Assertions.assertFalse(movieService.isBestPictureWinner(MovieServiceRequestDTO.builder().title("Titanic").rating(1f).build()));
    }

    /*
        Unit tests for rate movie feature
     */

    @Test
    public void whenInvalidRatingInput_thenThrowException(){
        Assertions.assertThrows(GenericException.class, () -> movieService.rateMovie(MovieServiceRequestDTO.builder().rating(30f).title("Titanic").build()));
    }

    @Test
    public void whenRatingNotPassed_thenThrowException(){
        Assertions.assertThrows(GenericException.class, () -> movieService.rateMovie(MovieServiceRequestDTO.builder().title("Titanic").rating(-1f).build()));
    }

    @Test
    public void whenMovieNotPresent_thenThrowException(){
        when(movieRepository.findByTitle(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(MovieNotFoundException.class, () -> movieService.rateMovie(MovieServiceRequestDTO.builder().rating(9f).build()));
    }

    @Test
    public void whenRatingPassed_thenAverageRatingUpdatedAndReturnSuccessResponse() {

        when(movieRepository.findByTitle(Mockito.any())).thenReturn(Optional.of(Movie.builder().title("Titanic").build()));
        when(usersRepository.findByName(Mockito.any())).thenReturn(Optional.of(Users.builder().id(1L).name("user1").build()));
        when(userMovieRatingRepository.findByMovieAndUser(Mockito.any(), Mockito.any())).thenReturn(
                Optional.of(UserMovieRating.builder()
                    .movie(Movie.builder().title("Titanic").build())
                    .rating(5f)
                    .user(Users.builder().id(1L).build())
                    .build()));
        when(userMovieRatingRepository.save(Mockito.any())).thenReturn(
                UserMovieRating.builder()
                        .movie(Movie.builder().title("Titanic").build())
                        .rating(5f)
                        .user(Users.builder().id(1L).build())
                        .build());
        when(userMovieRatingRepository.findAverageRatingByMovie(Mockito.any())).thenReturn(5d);
        when(movieRepository.save(Mockito.any())).thenReturn(Movie.builder().averageRating(5d).build());

        Movie response = movieService.rateMovie(MovieServiceRequestDTO.builder().rating(4f).title("Titanic").user(Users.builder().id(1L).name("user1").build()).build());
        Assertions.assertEquals(5f, response.getAverageRating());
    }

    /*
        Unit tests for 10topRatedMovies functionality
     */

    @Test
    public void whenMoviesNotAvailable_thenThrowException() {
        when(movieRepository.findAllByOrderByAverageRatingDesc()).thenReturn(Collections.EMPTY_LIST);
        Assertions.assertThrows(GenericException.class, () -> movieService.getTopRatedMovies());
    }
}
