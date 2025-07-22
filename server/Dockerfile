FROM maven:3.9.9-eclipse-temurin-21-jammy AS build
RUN mkdir -p /app
COPY server/src /app/src
COPY server/pom.xml /app
WORKDIR /app

RUN mvn verify

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY .env .
EXPOSE 443
ENTRYPOINT ["java", "-jar", "app.jar"]