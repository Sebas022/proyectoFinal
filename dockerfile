# Etapa 1: construir el .jar
FROM maven:3.9.6-amazoncorretto-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: imagen final con Corretto
FROM amazoncorretto:17
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENV DATABASE_URL=jdbc:postgresql://dpg-d4baveje5dus73ehg0k0-a.db.render.com:5432/bd_outfitfinder
ENV DATABASE_USERNAME=backend_admin
ENV DATABASE_PASSWORD=jPcXBKOWl5v1JaxfGN0qmtIvN8isFEXK

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]