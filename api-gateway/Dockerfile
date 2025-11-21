# ==========================
# Etapa 1: Build del JAR con Maven
# ==========================
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copiar TODO el proyecto multi-módulo
COPY . .

# Compilar SOLO api-gateway, resolviendo las dependencias necesarias
RUN mvn -f /app/pom.xml clean package -pl api-gateway -am -DskipTests

# ==========================
# Etapa 2: Imagen final (solo el JAR)
# ==========================
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiar el JAR del módulo api-gateway
COPY --from=builder /app/api-gateway/target/*.jar app.jar

# Exponer el puerto del gateway
EXPOSE 8090

# Ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
