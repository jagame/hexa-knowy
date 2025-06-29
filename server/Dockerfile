FROM maven:3.9.9-eclipse-temurin-21-jammy AS build
RUN mkdir -p /app
COPY src /app/src
COPY pom.xml /app
WORKDIR /app
RUN mvn verify -DskipTests

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY .env .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]