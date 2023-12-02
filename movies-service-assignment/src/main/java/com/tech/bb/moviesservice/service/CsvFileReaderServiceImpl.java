package com.tech.bb.moviesservice.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.tech.bb.moviesservice.exception.GenericException;
import com.tech.bb.moviesservice.model.MovieCsvDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

@Service
public class CsvFileReaderServiceImpl implements FileReaderService {

    @Value("${movie.service.csv.file.path}")
    private String csvFilePath;

    @Override
    public List<MovieCsvDTO> readMovieData() {
        try {
            Reader reader = new FileReader(csvFilePath);
            CsvToBean<MovieCsvDTO> csvToBean = new CsvToBeanBuilder<MovieCsvDTO>(reader)
                    .withType(MovieCsvDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();
        } catch (FileNotFoundException e) {
            throw new GenericException(e.getMessage());
        }
    }
}
