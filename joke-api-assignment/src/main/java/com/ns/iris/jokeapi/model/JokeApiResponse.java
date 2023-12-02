package com.ns.iris.jokeapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JokeApiResponse {

    @Schema(description = "Unique id for the Joke.")
    private int id;

    @Schema(description = "Random and decent Joke.")
    private String randomJoke;
}
