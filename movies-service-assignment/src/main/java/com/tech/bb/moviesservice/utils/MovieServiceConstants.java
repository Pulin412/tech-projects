package com.tech.bb.moviesservice.utils;

public final class MovieServiceConstants {

    private MovieServiceConstants(){}

    public static String CATEGORY_BEST_PICTURE = "Best Picture";
    public static String BEST_PICTURE_WON = "YES";

    // Exceptions
    public static String EXCEPTION_INVALID_USER = "Invalid User";
    public static String EXCEPTION_MISSING_API_KEY = "API Key is missing in the Request";
    public static String EXCEPTION_MISSING_TITLE = "Movie Title not provided in the request";
    public static String EXCEPTION_MISSING_MOVIE = "Movie not present in the Database";
    public static String EXCEPTION_INCORRECT_RATING = "Movie Ratings can only be from 0 to 10";
    public static String EXCEPTION_MOVIES_NOT_FOUND_FOR_TOP_RATINGS_LIST = "Movies data not present";
    public static String EXCEPTION_OMDB_API = "Unable to fetch Movie details from OMDB API";
    public static String BOX_OFFICE_NOT_AVAILABLE_STRING = "N/A";
    public static String OMDB_RESPONSE_VALID_STATUS = "True";
    public static String OMDB_BOX_OFFICE_DEFAULT_VALUE = "0";
    public static Integer NUMBER_OF_TOP_RATED_MOVIES = 10;

}
