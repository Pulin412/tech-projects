package com.tech.bb.moviesservice.model;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TopRatedMoviesResponseDTO {
    private String title;
    private Long boxOffice;
    private Double rating;
}
