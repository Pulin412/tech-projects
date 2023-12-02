package com.ns.iris.jokeapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlagDto {

    private boolean sexist;
    private boolean explicit;
}
