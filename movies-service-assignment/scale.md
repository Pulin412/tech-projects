# 'movies-service'

## How to scale

- Implement a load balancer to evenly distribute incoming traffic among multiple instances of the service running on different servers or containers.
- Set up a database cluster or a distributed cache to handle the increased load and provide high availability and fault tolerance.
- Store the CSV records in a distributed cache for faster and more efficient access.
- Utilize a content delivery network (CDN) to cache static content such as movie posters (in case the solution extends to handle movie posters) and reduce response times for users.
- To optimize calling the OMDb API multiple times, asynchronously store the BoxOffice values for the top 10 rated movies and keep them in cache for quick access.