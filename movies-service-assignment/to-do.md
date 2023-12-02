# 'movies-service'

## ToDos / Production Readiness

- During Production, we ensure that the application's Readiness probe does not pass until the data has been loaded.
- We should make the data load available in cache to improve the performance of the application.
- Instead of using `spring.jpa.hibernate.ddl-auto=create`, we can use `Flyway migrations` for managing the database schema in Production.
- We can use a custom converter using the OpenCSV library to map the String value for `Won?` from the CSV file to a boolean parameter in the mapped DTO model `MovieCsvDTO`.
- Implement a retry mechanism and/or circuit breaker for `OmdbApiMovieGatewayServiceImpl.getMovieData` to handle connectivity issues when trying to connect to the OMDb API.
- We can create an ENUM for `Category` in the Category table for type safety and better query performance.
- Add authentication and authorization to the API endpoints to ensure secure access to the service. Instead of using `OncePerRequestFilter`, we can use Spring Security with specialized APIs.
- `apiKey` in the header should be encrypted both in transit and at rest.
- Create a separate validation service and implementations to validate title/ratings to decouple it from the main service. Add more validations.
- Improve error handling and validation to provide meaningful error messages to the user.