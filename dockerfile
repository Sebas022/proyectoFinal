# Etapa 1: construir el .jar
FROM maven:3.9.6-amazoncorretto-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: imagen final con Corretto
FROM amazoncorretto:17
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENV DATABASE_URL=jdbc:postgresql://localhost:5432/bd_OutfitFinder
ENV DATABASE_USERNAME=postgres

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]