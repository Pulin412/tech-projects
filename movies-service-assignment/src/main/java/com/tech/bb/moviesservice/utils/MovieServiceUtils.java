package com.tech.bb.moviesservice.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MovieServiceUtils {

    public static long sanitizeBoxOfficeValue(String boxOfficeValStr){
        try {
            String boxOfficeValStrWithoutSymbols = boxOfficeValStr.replaceAll("[^\\d.]", "");
            return Long.parseLong(boxOfficeValStrWithoutSymbols);
        } catch (NumberFormatException e){
            log.error("sanitizeBoxOfficeValue ::: Unable to sanitize box office value {}, returning 0", boxOfficeValStr);
            return 0;
        }
    }
}
