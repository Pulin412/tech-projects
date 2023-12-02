# 'movies-service'

## How to test

- Run `mvn test` to test and run the Unit and End-to-End test cases.
- 2 dummy users are created using `src/main/resources/data.sql` to test the API.
- The API key for the OMDb API is stored in `application.properties`, which can be easily updated to a new key obtained from the OMDb API.

- Once the application is up and the initial data load is done, use following cURL commands for the endpoints:

    - Rate a movie :
      ```shell
      curl --location 'http://localhost:8080/movies/rate' \
      --header 'apiKey: xyz456' \
      --header 'Content-Type: application/json' \
      --data '{
          "title" : "The Godfather",
          "rating" : 10
      }'
      ```

    - Check if a movie title has won best picture :
      ```shell
       curl --location 'http://localhost:8080/movies/best-picture?title=Rocky' \
       --header 'apiKey: xyz456'
      ```

    - Get Top 10 Rated Movies order by Box office value :
      ```shell
        curl --location 'http://localhost:8080/movies/top-rated' \
        --header 'apiKey: xyz456'
      ```