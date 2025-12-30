# Usamos la imagen de Maven con Java 17
FROM maven:3.9.5-eclipse-temurin-17
WORKDIR /app

# 1. Copiamos el pom primero para cachear las librerías (esto ahorra mucho tiempo)
COPY pom.xml .
RUN mvn dependency:go-offline

# 2. Copiamos el código fuente
COPY src ./src

# 3. Compilamos y generamos el JAR
RUN mvn clean package -DskipTests

# 4. Exponemos el puerto
EXPOSE 8080

# 5. Ejecutamos directamente el JAR generado en la carpeta target
# Usamos un comando de shell para encontrar el nombre del jar automáticamente
CMD ["sh", "-c", "java -jar target/*.jar"]