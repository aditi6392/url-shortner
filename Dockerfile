# Use a Maven image with Java 23 to build the Spring Boot app
FROM eclipse-temurin:23-jdk AS build

# Set the working directory
WORKDIR /app

# Copy Maven Wrapper files
COPY mvnw .
COPY .mvn .mvn

# Give execute permission to mvnw
RUN chmod +x mvnw

# Copy pom.xml separately to leverage Docker cache
COPY pom.xml .

# Download dependencies (optional but helps caching)
RUN ./mvnw dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Build the Spring Boot application (skip tests to speed up)
RUN ./mvnw clean package -DskipTests

# Use a Java 23 runtime image to run the app
FROM eclipse-temurin:23-jre

# Set the working directory
WORKDIR /app

# Copy the built JAR from the previous build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
