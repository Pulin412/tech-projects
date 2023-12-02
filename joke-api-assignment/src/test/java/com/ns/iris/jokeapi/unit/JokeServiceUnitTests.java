package com.ns.iris.jokeapi.unit;

import com.ns.iris.jokeapi.exception.JokesNotFoundException;
import com.ns.iris.jokeapi.exception.NoDecentJokesFoundException;
import com.ns.iris.jokeapi.model.FlagDto;
import com.ns.iris.jokeapi.model.JokeApiResponse;
import com.ns.iris.jokeapi.model.JokeDto;
import com.ns.iris.jokeapi.model.ResponseDto;
import com.ns.iris.jokeapi.service.JokeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class JokeServiceUnitTests {

    @Mock
    private final RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
    @InjectMocks
    private final JokeServiceImpl jokeService = new JokeServiceImpl();

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    /*
        Test Case 1: When jokes are returned from the external API, respond with the shortest and decent joke.
     */
    @Test
    public void whenAllDecentJokesFetched_thenReturnShortestJoke(){
        List<JokeDto> jokes = new ArrayList<>();
        jokes.add(JokeDto.builder().joke("joke").id(1).safe(true).flags(FlagDto.builder().explicit(false).sexist(false).build()).build());
        jokes.add(JokeDto.builder().joke("longJoke").id(2).safe(true).flags(FlagDto.builder().explicit(false).sexist(false).build()).build());
        jokes.add(JokeDto.builder().joke("longerJoke").id(3).safe(true).flags(FlagDto.builder().explicit(false).sexist(false).build()).build());
        jokes.add(JokeDto.builder().joke("longestJoke").id(4).safe(true).flags(FlagDto.builder().explicit(false).sexist(false).build()).build());

        ResponseDto responseDto = ResponseDto.builder()
                .jokes(jokes)
                .error(false)
                .build();

        ResponseEntity<ResponseDto> response= new ResponseEntity<>(responseDto, HttpStatus.OK);
        when(restTemplate.exchange(Mockito.anyString(),
                Mockito.any(),
                Mockito.<HttpEntity<ResponseDto>> any(),
                Mockito.<Class<ResponseDto>> any(),
                Mockito.anyMap()))
                .thenReturn(response);

        ReflectionTestUtils.setField(jokeService,"externalUri", "https://v2.jokeapi.dev/joke/Any");
        JokeApiResponse actualResponse = jokeService.fetchJokes("single", 10);
        Assertions.assertEquals(1, actualResponse.getId());
    }

    /*
        Test Case 2: When no jokes are returned from the external API, throw JokesNotFoundException.
     */
    @Test
    public void whenNoJokesFetched_thenThrowException(){
        ResponseDto responseDto = ResponseDto.builder()
                .jokes(null)
                .error(false)
                .build();

        ResponseEntity<ResponseDto> response= new ResponseEntity<>(responseDto, HttpStatus.OK);
        when(restTemplate.exchange(Mockito.anyString(),
                Mockito.any(),
                Mockito.<HttpEntity<ResponseDto>> any(),
                Mockito.<Class<ResponseDto>> any(),
                Mockito.anyMap()))
                .thenReturn(response);

        ReflectionTestUtils.setField(jokeService,"externalUri", "https://v2.jokeapi.dev/joke/Any");
        Assertions.assertThrows(JokesNotFoundException.class, () -> jokeService.fetchJokes("single", 10));
    }

    /*
        Test Case 3: When jokes are returned from the external API but all jokes are indecent(not safe/sexist/explicit), throw NoDecentJokesFoundException.
     */
    @Test
    public void whenAllUnsafeJokesFetched_thenThrowException(){
        List<JokeDto> jokes = new ArrayList<>();
        jokes.add(JokeDto.builder().joke("joke").id(1).safe(false).flags(FlagDto.builder().explicit(false).sexist(false).build()).build());
        jokes.add(JokeDto.builder().joke("longJoke").id(2).safe(false).flags(FlagDto.builder().explicit(false).sexist(false).build()).build());
        jokes.add(JokeDto.builder().joke("longerJoke").id(3).safe(false).flags(FlagDto.builder().explicit(false).sexist(false).build()).build());
        jokes.add(JokeDto.builder().joke("longestJoke").id(4).safe(false).flags(FlagDto.builder().explicit(false).sexist(false).build()).build());

        ResponseDto responseDto = ResponseDto.builder()
                .jokes(jokes)
                .error(false)
                .build();

        ResponseEntity<ResponseDto> response= new ResponseEntity<>(responseDto, HttpStatus.OK);
        when(restTemplate.exchange(Mockito.anyString(),
                Mockito.any(),
                Mockito.<HttpEntity<ResponseDto>> any(),
                Mockito.<Class<ResponseDto>> any(),
                Mockito.anyMap()))
                .thenReturn(response);

        ReflectionTestUtils.setField(jokeService,"externalUri", "https://v2.jokeapi.dev/joke/Any");
        Assertions.assertThrows(NoDecentJokesFoundException.class, () -> jokeService.fetchJokes("single", 10));
    }

    /*
        Test Case 4: When only 1 joke returned is decent from all the returned jokes from the external API, respond with that joke.
     */
    @Test
    public void whenSingleDecentJokeFetched_thenReturnTheSameJoke(){
        List<JokeDto> jokes = new ArrayList<>();
        jokes.add(JokeDto.builder().joke("joke").id(1).safe(false).flags(FlagDto.builder().explicit(false).sexist(false).build()).build());
        jokes.add(JokeDto.builder().joke("longJoke").id(2).safe(true).flags(FlagDto.builder().explicit(true).sexist(false).build()).build());
        jokes.add(JokeDto.builder().joke("longerJoke").id(3).safe(true).flags(FlagDto.builder().explicit(false).sexist(true).build()).build());
        jokes.add(JokeDto.builder().joke("longestJoke").id(4).safe(true).flags(FlagDto.builder().explicit(false).sexist(false).build()).build());

        ResponseDto responseDto = ResponseDto.builder()
                .jokes(jokes)
                .error(false)
                .build();

        ResponseEntity<ResponseDto> response= new ResponseEntity<>(responseDto, HttpStatus.OK);
        when(restTemplate.exchange(Mockito.anyString(),
                Mockito.any(),
                Mockito.<HttpEntity<ResponseDto>> any(),
                Mockito.<Class<ResponseDto>> any(),
                Mockito.anyMap()))
                .thenReturn(response);

        ReflectionTestUtils.setField(jokeService,"externalUri", "https://v2.jokeapi.dev/joke/Any");
        JokeApiResponse actualResponse = jokeService.fetchJokes("single", 10);
        Assertions.assertEquals(4, actualResponse.getId());
    }

}
