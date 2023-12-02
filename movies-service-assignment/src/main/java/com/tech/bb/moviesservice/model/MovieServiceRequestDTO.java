package com.tech.bb.moviesservice.model;

import com.tech.bb.moviesservice.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieServiceRequestDTO {

    private String title;
    private float rating;
    private Users user;
}
