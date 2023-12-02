package com.tech.bb.moviesservice.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MovieCsvDTO {
    @CsvBindByPosition(position = 0)
    private String year;
    @CsvBindByPosition(position = 1)
    private String category;
    @CsvBindByPosition(position = 2)
    private String nominee;
    @CsvBindByPosition(position = 3)
    private String additionalInfo;
    @CsvBindByPosition(position = 4)
    private String won;
}
