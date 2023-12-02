# 'movies-service'

## How to run

- To start the application, use the command `mvn spring-boot:run` in the terminal.
- The initial data will be loaded into the database from the CSV file located at `src/main/resources/academy_awards.csv` when the application starts. 
- You will see a log message indicating that the data is being loaded:
  > Data Initializer ::: Loading DB with the CSV file contents
  
- Please wait for the data to finish loading. It may take up to 2 minutes. Once the data has finished loading, you will see a new log message:
  > Data Initializer ::: Loading completed