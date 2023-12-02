package com.tech.bb.moviesservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalMovieGatewayDTO {

    private String title;
    private String awards;
    private String boxOffice;
    private String response;

    @JsonProperty("Title")
    public String getTitle() {
        return this.title;
    }

    @JsonProperty("Awards")
    public String getAwards() {
        return this.awards;
    }

    @JsonProperty("BoxOffice")
    public String getBoxOffice() {
        return this.boxOffice;
    }

    @JsonProperty("Response")
    public String getResponse() {
        return this.response;
    }
}


