# ---- Build stage ----
FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn -q -e -B -DskipTests dependency:go-offline
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn -q -e -B -DskipTests package

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app
# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar
# Defaults can be overridden from docker compose
ENV SERVER_PORT=8080
EXPOSE 8080
# Allow overriding arbitrary Spring properties by env vars
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${SERVER_PORT} -jar /app/app.jar"]
