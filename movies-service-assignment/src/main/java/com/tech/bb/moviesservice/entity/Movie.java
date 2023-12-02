package com.tech.bb.moviesservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = @Index(columnList = "title"))
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    private String yearOfAward;
    private String additionalInfo;
    private Boolean won;

    private Double averageRating;

    @ManyToMany(cascade = {
                    CascadeType.ALL
            }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "movie_category",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    Set<Category> movieCategories;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<UserMovieRating> userMovieRatings;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie movie)) return false;
                return Objects.equals(title, movie.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
