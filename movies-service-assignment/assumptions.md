# 'movies-service'

## Assumptions

- The external API and CSV file are dependable and consistently updated.
- There are no concurrent API calls from users attempting to submit ratings simultaneously.
- The ratings provided by the users are accurate.
- The users possess the necessary permissions and authorization to rate movies and access the service.
- The users are familiar with the movie titles and the Best Picture award.
- To verify whether a given movie has won the Best Picture award, we only check the CSV file, not the OMDb API. The OMDb API simply provides information on the number of awards a movie has won, but not for any specific category.
- When returning the Top 10 rated movies arranged by box office value, if the Box Office value from the OMDb API comes back as N/A, the code uses 0 box value. As a refinement, we could exclude this movie entirely and select a different one.