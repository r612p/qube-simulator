# Use official Java runtime base image
FROM openjdk:17-jdk-slim

# Add the packaged jar to the container
COPY C:\Users\ryanp\Downloads\simulator\simulator\target\simulator-0.0.1-SNAPSHOT.jar.original  

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]

# Expose port 8080
EXPOSE 8080
