# Base image with Java 17
FROM maven:3.8.4-openjdk-17

# working directory
WORKDIR /user

# Copy the source code
COPY . /user

# Copy jar file
COPY user-service/target/user-service-0.0.1-SNAPSHOT.jar /user/user-service-0.0.1-SNAPSHOT.jar

# Command to run the application
CMD ["java", "-jar", "/user/user-service-0.0.1-SNAPSHOT.jar"]

