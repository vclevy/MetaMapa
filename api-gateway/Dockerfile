# ==========================
# Etapa 1: Build del JAR con Maven
# ==========================
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /build

# Copiamos TODO el proyecto (incluye pom padre, módulos, hijos, etc)
COPY .. .

# Compilamos SOLO el módulo api-gateway usando el POM PADRE
RUN mvn -f pom.xml clean package -pl api-gateway -am -DskipTests

# ==========================
# Etapa 2: Imagen final
# ==========================
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiar el jar generado
COPY --from=builder /build/api-gateway/target/*.jar app.jar

EXPOSE 8090
ENTRYPOINT ["java", "-jar", "app.jar"]
