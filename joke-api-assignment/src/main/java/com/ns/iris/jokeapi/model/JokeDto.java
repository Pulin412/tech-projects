package com.ns.iris.jokeapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class JokeDto {

    private String joke;
    private FlagDto flags;
    private int id;
    private boolean safe;
}
