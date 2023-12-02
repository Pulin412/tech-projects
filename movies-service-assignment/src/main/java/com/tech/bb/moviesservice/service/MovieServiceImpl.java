package com.tech.bb.moviesservice.service;

import com.tech.bb.moviesservice.entity.Movie;
import com.tech.bb.moviesservice.entity.UserMovieRating;
import com.tech.bb.moviesservice.entity.UserMovieRatingId;
import com.tech.bb.moviesservice.entity.Users;
import com.tech.bb.moviesservice.exception.GenericException;
import com.tech.bb.moviesservice.exception.MovieNotFoundException;
import com.tech.bb.moviesservice.exception.UserAuthenticationException;
import com.tech.bb.moviesservice.gateway.MovieGatewayService;
import com.tech.bb.moviesservice.model.ExternalMovieGatewayDTO;
import com.tech.bb.moviesservice.model.ExternalMovieGatewayRequestDTO;
import com.tech.bb.moviesservice.model.MovieServiceRequestDTO;
import com.tech.bb.moviesservice.model.TopRatedMoviesResponseDTO;
import com.tech.bb.moviesservice.repository.MovieRepository;
import com.tech.bb.moviesservice.repository.UserMovieRatingRepository;
import com.tech.bb.moviesservice.repository.UsersRepository;
import com.tech.bb.moviesservice.utils.MovieServiceConstants;
import com.tech.bb.moviesservice.utils.MovieServiceUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final UserMovieRatingRepository userMovieRatingRepository;
    private final UsersRepository usersRepository;
    private final MovieGatewayService movieGatewayService;

    public MovieServiceImpl(MovieRepository movieRepository, UserMovieRatingRepository userMovieRatingRepository, UsersRepository usersRepository, MovieGatewayService movieGatewayService) {
        this.movieRepository = movieRepository;
        this.userMovieRatingRepository = userMovieRatingRepository;
        this.usersRepository = usersRepository;
        this.movieGatewayService = movieGatewayService;
    }

    @Override
    public boolean isBestPictureWinner(MovieServiceRequestDTO movieServiceRequestDTO) {
        validateTitle(movieServiceRequestDTO);
        String title = movieServiceRequestDTO.getTitle();

        Movie movie = movieRepository.findByTitle(title)
                .orElseThrow(() -> new MovieNotFoundException(MovieServiceConstants.EXCEPTION_MISSING_MOVIE));

        return movie.getMovieCategories().stream()
                .anyMatch(category -> category.getCategory().equalsIgnoreCase(MovieServiceConstants.CATEGORY_BEST_PICTURE) && movie.getWon());
    }


    @Override
    @Transactional
    public Movie rateMovie(MovieServiceRequestDTO movieServiceRequestDTO) {

        validateTitle(movieServiceRequestDTO);
        validateRating(movieServiceRequestDTO);

        String title = movieServiceRequestDTO.getTitle();
        Movie movie = movieRepository.findByTitle(title)
                .orElseThrow(() -> new MovieNotFoundException(MovieServiceConstants.EXCEPTION_MISSING_MOVIE));

        // save/update the incoming rating for the User
        saveUserRating(movie, movieServiceRequestDTO);

        // (re)calculate the average rating for the current movie and update the Movie table
        Double avgRating = userMovieRatingRepository.findAverageRatingByMovie(movie.getId());
        avgRating = avgRating != null ? Math.round(avgRating * 10.0) / 10.0 : 0.0;  // round off to 1 decimal place

        movie.setAverageRating(avgRating);
        return movieRepository.save(movie);
    }

    private void saveUserRating(Movie movie, MovieServiceRequestDTO movieServiceRequestDTO) {
        Users user = usersRepository.findByName(movieServiceRequestDTO.getUser().getName())
                .orElseThrow(() -> new UserAuthenticationException(MovieServiceConstants.EXCEPTION_INVALID_USER));

        UserMovieRating userMovieRating = userMovieRatingRepository.findByMovieAndUser(movie, user)
                .orElse(UserMovieRating.builder()
                        .id(UserMovieRatingId.builder()
                                .userId(user.getId())
                                .movieId(movie.getId())
                                .build())
                        .user(user)
                        .build());

        userMovieRating.setRating(movieServiceRequestDTO.getRating());
        userMovieRating.setMovie(movie);
        userMovieRatingRepository.save(userMovieRating);
    }

    @Override
    public List<TopRatedMoviesResponseDTO> getTopRatedMovies() {

        List<Movie> top10RatedMoviesList = movieRepository.findAllByOrderByAverageRatingDesc();

        if (CollectionUtils.isEmpty(top10RatedMoviesList))
            throw new GenericException(MovieServiceConstants.EXCEPTION_MOVIES_NOT_FOUND_FOR_TOP_RATINGS_LIST);

        top10RatedMoviesList = top10RatedMoviesList.stream()
                .filter(movie -> movie.getMovieCategories().stream()
                        .anyMatch(category -> category.getCategory().equalsIgnoreCase(MovieServiceConstants.CATEGORY_BEST_PICTURE)))
                .limit(MovieServiceConstants.NUMBER_OF_TOP_RATED_MOVIES)
                .toList();

        /*
                1. For the top 10 rated movies, calculate the box office values for each movie using OMDb API.
                2. Create a Map with Movie title as key and fetched box office value.
                3. Return the generated list using the Map created in the last step.
         */
        return mapMoviesFromDbToMovieDtoList(top10RatedMoviesList)
                .stream()
                .sorted(Comparator.comparing(TopRatedMoviesResponseDTO::getBoxOffice).reversed())
                .collect(Collectors.toList());
    }

    private List<TopRatedMoviesResponseDTO> mapMoviesFromDbToMovieDtoList(List<Movie> movieList) {

        Map<String, Long> movieBoxOfficeMap = fetchBoxOfficeValuesFromOmdbApi(movieList);
        return movieList.stream()
                .map(movie -> TopRatedMoviesResponseDTO.builder()
                        .title(movie.getTitle())
                        .rating(movie.getAverageRating())
                        .boxOffice(movieBoxOfficeMap.get(movie.getTitle()))
                        .build())
                .collect(Collectors.toList());
    }

    private Map<String, Long> fetchBoxOfficeValuesFromOmdbApi(List<Movie> movieRatingList) {
        Map<String, Long> movieBoxOfficeMap = new HashMap<>();
        movieRatingList.forEach(movieRating -> {
            String title = movieRating.getTitle();

            ExternalMovieGatewayDTO response = movieGatewayService.getMovieData(ExternalMovieGatewayRequestDTO.builder().title(title).build());
            String boxOfficeValStr = response.getBoxOffice();

            if (Strings.isEmpty(boxOfficeValStr) || MovieServiceConstants.BOX_OFFICE_NOT_AVAILABLE_STRING.equalsIgnoreCase(boxOfficeValStr)) {
                boxOfficeValStr = MovieServiceConstants.OMDB_BOX_OFFICE_DEFAULT_VALUE;
            }

            log.info("MOVIE SERVICE ::: fetched box office value {} from OMDb API for {}", boxOfficeValStr, title);
            movieBoxOfficeMap.put(title, MovieServiceUtils.sanitizeBoxOfficeValue(boxOfficeValStr));
        });

        return movieBoxOfficeMap;
    }

    private void validateTitle(MovieServiceRequestDTO movieServiceRequestDTO) {

        log.info("MOVIE SERVICE ::: Validating title - {} from request", movieServiceRequestDTO.getTitle());
        if (Strings.isEmpty(movieServiceRequestDTO.getTitle()))
            throw new MovieNotFoundException(MovieServiceConstants.EXCEPTION_MISSING_TITLE);
    }

    private void validateRating(MovieServiceRequestDTO movieServiceRequestDTO) {

        log.info("MOVIE SERVICE ::: Validating rating - {} from request", movieServiceRequestDTO.getRating());
        if (movieServiceRequestDTO.getRating() < 0 || movieServiceRequestDTO.getRating() > 10)
            throw new GenericException(MovieServiceConstants.EXCEPTION_INCORRECT_RATING);
    }
}
