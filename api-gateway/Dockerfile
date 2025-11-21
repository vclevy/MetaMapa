# ==========================
# Etapa 1: Build del JAR con Maven
# ==========================
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copiar todo el repo
COPY . .

# Compilar solo el API Gateway usando el POM padre correcto
RUN mvn -f /app/2025-tpa-ma-ma-grupo-4/pom.xml clean package -pl api-gateway -am -DskipTests

# ==========================
# Etapa 2: Imagen final
# ==========================
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

COPY --from=builder /app/2025-tpa-ma-ma-grupo-4/api-gateway/target/*.jar app.jar

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "app.jar"]
