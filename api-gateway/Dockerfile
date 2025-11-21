# ==========================
# Etapa 1: Build del JAR con Maven
# ==========================
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copiar TODO el proyecto (incluyendo parent POM y módulos)
COPY . .

# Compilar solo el módulo del API Gateway sin tests
RUN mvn -f /app/pom.xml clean package -pl api-gateway -am -DskipTests

# ==========================
# Etapa 2: Imagen final (solo el JAR)
# ==========================
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiar el JAR compilado desde la etapa anterior
COPY --from=builder /app/api-gateway/target/*.jar app.jar

# Puerto del API Gateway
EXPOSE 8090

# Entrypoint
ENTRYPOINT ["java", "-jar", "app.jar"]
