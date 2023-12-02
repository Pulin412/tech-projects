package com.tech.bb.moviesservice.e2e;

import com.tech.bb.moviesservice.config.DataInitializer;
import com.tech.bb.moviesservice.model.MovieServiceRequestDTO;
import com.tech.bb.moviesservice.model.MovieServiceResponseDTO;
import com.tech.bb.moviesservice.model.TopRatedMovieResponseWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovieControllerE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private DataInitializer dataInitializer;
    @BeforeAll
    public void initializeData(){
        dataInitializer.initializeData();
    }

    @Test
    void testGetTopRatedMovies() {
        String apiKey = "xyz456";
        HttpHeaders headers = new HttpHeaders();
        headers.set("apiKey", apiKey);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<TopRatedMovieResponseWrapper> response = restTemplate.exchange(
                "/movies/top-rated",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getTopRatedMoviesResponseDTOList().isEmpty());
    }

    @Test
    void testRateMovie() {
        String apiKey = "xyz456";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apiKey", apiKey);

        HttpEntity<MovieServiceRequestDTO> entity = new HttpEntity<>(MovieServiceRequestDTO.builder().title("The Godfather").rating(9.5f).build(), headers);

        ResponseEntity<MovieServiceResponseDTO> response = restTemplate.postForEntity("/movies/rate", entity, MovieServiceResponseDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void isBestPictureWinner_WhenTitleIsBestPicture_ShouldReturnTrue() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apiKey", "xyz456");

        ResponseEntity<MovieServiceResponseDTO> response = restTemplate.exchange(
                "/movies/best-picture?title=Titanic",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                MovieServiceResponseDTO.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Titanic has won the Best Picture Award", Objects.requireNonNull(response.getBody()).getResponse());
    }

    @Test
    void isBestPictureWinner_WhenApiKeyIsInvalid_ShouldReturnUnauthorized() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apiKey", "invalidApiKey");

        ResponseEntity<MovieServiceResponseDTO> response = restTemplate.exchange(
                "/movies/best-picture?title=Titanic",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                MovieServiceResponseDTO.class);

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
