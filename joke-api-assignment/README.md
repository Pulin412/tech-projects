# NS - IRIS - Joke API

Spring boot based service with one end point to fetch Jokes from the external Joke API.

## Steps to run
- Build the docker image - 
```
docker build --tag=joke-api:latest .
```
- Run the docker image:
```
docker run -p8087:8080 joke-api:latest
```
- Run the swagger end point at:
> http://localhost:8087/swagger-ui/index.html#/


### To run the application directly using Spring boot maven plugin:
```
mvn spring-boot:run
```
- Run the swagger end point at:
> http://localhost:8080/swagger-ui/index.html#/
