# official mavrn image to build app
FROM maven:3.9.4-eclipse-temurin-17 AS build

# working directory set
WORKDIR /app

# copy code to container
COPY . .

# build w maven
RUN mvn clean package -DskipTests

# jdk to run app
FROM eclipse-temurin:17-jdk-alpine

# another directory
WORKDIR /app

# Copying the built JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Command to run program
ENTRYPOINT ["java", "-jar", "app.jar"]
