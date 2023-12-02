package com.ns.iris.jokeapi.e2e;

import com.ns.iris.jokeapi.model.JokeApiResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JokesApiE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    /*
        E2E Test Case 1: When external API called with valid parameters, valid response is returned with Jokes.
     */
    @Test
    public void whenExternalJokeApiCalledWithValidParameters_thenValidResponseReturned(){

        URI targetUrl= UriComponentsBuilder.fromUriString("/joke")
                .queryParam("type", "single")
                .queryParam("amount", 10)
                .build()
                .encode()
                .toUri();

        ResponseEntity<JokeApiResponse> response = restTemplate.getForEntity(targetUrl, JokeApiResponse.class);
        Assertions.assertTrue(response.hasBody());
    }

    /*
        E2E Test Case 2: When external API called with invalid parameters, valid response is returned but with no Jokes in the response.
     */
    @Test
    public void whenExternalJokeApiCalledWithInValidParameters_thenNoResponseReturned(){

        URI targetUrl= UriComponentsBuilder.fromUriString("/joke")
                .queryParam("type", "single")
                .queryParam("amount", 1)
                .build()
                .encode()
                .toUri();

        ResponseEntity<JokeApiResponse> response = restTemplate.getForEntity(targetUrl, JokeApiResponse.class);
        Assertions.assertNull(Objects.requireNonNull(response.getBody()).getRandomJoke());
    }
}
