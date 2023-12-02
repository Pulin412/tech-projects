package com.tech.bb.moviesservice.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserMovieRatingId implements Serializable {
    private Long userId;
    private Long movieId;
}

