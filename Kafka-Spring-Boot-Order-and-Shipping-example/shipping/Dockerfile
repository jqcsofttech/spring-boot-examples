# Etapa 1: Build con Maven y Java 11 (Temurin)
FROM maven:3.9.5-eclipse-temurin-11 AS build
WORKDIR /app

# Copiar el pom y descargar dependencias (esto acelera futuras builds)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código y compilar
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Runtime ligero
FROM eclipse-temurin:11-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]