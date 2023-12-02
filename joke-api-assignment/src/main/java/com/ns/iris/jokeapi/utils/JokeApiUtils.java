package com.ns.iris.jokeapi.utils;

import com.ns.iris.jokeapi.exception.NoDecentJokesFoundException;
import com.ns.iris.jokeapi.model.JokeApiResponse;
import com.ns.iris.jokeapi.model.JokeDto;
import com.ns.iris.jokeapi.model.ResponseDto;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class JokeApiUtils {

    public static JokeApiResponse mapExternalResponseToJokeApiResponse(ResponseDto responseDto) {

        List<JokeDto> returnedJokes = responseDto.getJokes();

        // trim the result from the external API for only the decent jokes - Non sexist/non explicit and safe.
        List<JokeDto> decentJokesList = returnedJokes.stream()
                .filter(jokeDto -> jokeDto.isSafe() && !(jokeDto.getFlags().isSexist() || jokeDto.getFlags().isExplicit()))
                .collect(Collectors.toList());

        if(CollectionUtils.isEmpty(decentJokesList))
            throw new NoDecentJokesFoundException(JokeApiConstants.NO_DECENT_JOKES_FOUND_EXCEPTION);

        // If after trimming, only 1 joke is present, just return the same joke instead of going and fetching the shortest joke
        if(decentJokesList.size() == 1)
            return JokeApiResponse.builder().id(decentJokesList.get(0).getId()).randomJoke(decentJokesList.get(0).getJoke()).build();

        // Find the shortest joke if more than 1 jokes are present at this point
        JokeDto shortestJoke = findShortestJoke(decentJokesList);
        return JokeApiResponse.builder().id(shortestJoke.getId()).randomJoke(shortestJoke.getJoke()).build();
    }

    private static JokeDto findShortestJoke(List<JokeDto> returnedJokes) {

        Comparator<JokeDto> customComparator = Comparator.comparing(JokeDto::getJoke, Comparator.comparingInt(String::length));
        return returnedJokes.stream().min(customComparator).get();
    }
}
