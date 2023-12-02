package com.tech.bb.moviesservice.config;

import com.tech.bb.moviesservice.entity.Category;
import com.tech.bb.moviesservice.entity.Movie;
import com.tech.bb.moviesservice.model.MovieCsvDTO;
import com.tech.bb.moviesservice.repository.CategoryRepository;
import com.tech.bb.moviesservice.repository.MovieRepository;
import com.tech.bb.moviesservice.repository.UserMovieRatingRepository;
import com.tech.bb.moviesservice.service.FileReaderService;
import com.tech.bb.moviesservice.utils.MovieServiceConstants;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class DataInitializer {

    private final MovieRepository movieRepository;
    private final FileReaderService fileReaderService;
    private final UserMovieRatingRepository userMovieRatingRepository;
    private final CategoryRepository categoryRepository;

    public DataInitializer(MovieRepository movieRepository, FileReaderService fileReaderService, UserMovieRatingRepository userMovieRatingRepository, CategoryRepository categoryRepository) {
        this.movieRepository = movieRepository;
        this.fileReaderService = fileReaderService;
        this.userMovieRatingRepository = userMovieRatingRepository;
        this.categoryRepository = categoryRepository;
    }

    public void initializeData(){
        log.info("Data Initializer ::: Loading DB with the CSV file contents");
        loadDataFromCsvToDb();
        log.info("Data Initializer ::: Loading completed");
    }

    private void loadDataFromCsvToDb() {
        fileReaderService.readMovieData().stream()
                .skip(1)
                .forEach(this::saveOrUpdateMovieWithCategory);
    }

    @Transactional
    public void saveOrUpdateMovieWithCategory(MovieCsvDTO movieCsvDto) {

        try{
        Movie movie = createOrUpdateMovie(mapMovieEntityFromCsvDto(movieCsvDto), movieCsvDto.getCategory());
        // generate random rating
        movie.setAverageRating(Math.round((new Random().nextDouble() * 9 + 1) * 10.0) / 10.0);
        movieRepository.save(movie);
        } catch (Exception e){
            log.error("Error while saving data from CSV , skipping ", e);
        }
    }

    private Movie mapMovieEntityFromCsvDto(MovieCsvDTO movieCsvDto) {
        /*
                Trim the string if the string is more than 255 chars to store in the DB.
                Other option is to increase the size of column in DB using the command -

                    ALTER TABLE movie_csv ALTER COLUMN title TYPE VARCHAR(500);

                This command would increase the maximum length of the title column to 500 characters.
                Although this may have implications for the size of the database and the performance of queries.
             */
        String additionalInfo = Optional.ofNullable(movieCsvDto.getAdditionalInfo())
                .map(str -> str.length() > 254 ? str.substring(0, 254) : str)
                .orElse(null);
        String title = Optional.ofNullable(movieCsvDto.getNominee())
                .map(str -> str.length() > 254 ? str.substring(0, 254) : str)
                .orElse(null);

        return Movie.builder()
                .title(title)
                .yearOfAward(movieCsvDto.getYear())
                .additionalInfo(additionalInfo)
                .won(Objects.equals(movieCsvDto.getWon(), MovieServiceConstants.BEST_PICTURE_WON))
                .build();
    }

    private Movie createOrUpdateMovie(Movie mappedMovieEntity, String categoryName) {
        Optional<Movie> movieOptional = movieRepository.findByTitle(mappedMovieEntity.getTitle());
        Movie movieFromDb;
        if (movieOptional.isPresent()) {
            movieFromDb = movieOptional.get();
            return updateMovie(movieFromDb, mappedMovieEntity, categoryName);
        } else {
            return createMovie(mappedMovieEntity, categoryName);
        }
    }

    private Movie createMovie(Movie movie, String categoryName) {
        movieRepository.save(movie);
        Category category = getCategory(categoryName);
        Set<Category> categories = new HashSet<>();
        categories.add(category);
        movie.setMovieCategories(categories);

        return movie;
    }

    private Movie updateMovie(Movie movieFromDb, Movie mappedMovieEntity, String categoryName) {
        Set<Category> categories = movieFromDb.getMovieCategories() != null ? movieFromDb.getMovieCategories() : new HashSet<>();

        Category category = getCategory(categoryName);
        categories.add(category);
        movieFromDb.setMovieCategories(categories);

        movieFromDb.setYearOfAward(mappedMovieEntity.getYearOfAward());
        movieFromDb.setAdditionalInfo(mappedMovieEntity.getAdditionalInfo());
        movieFromDb.setWon(mappedMovieEntity.getWon());
        return movieFromDb;
    }

    private Category getCategory(String categoryName) {
        Optional<Category> categoryOptional = categoryRepository.findByCategory(categoryName);
        return categoryOptional.orElseGet(() -> categoryRepository.save(Category.builder().category(categoryName).build()));
    }
}
