# ----------- STAGE 1: BUILD -----------
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copiamos TODO el repo (necesario para multimódulo)
COPY . .

# Compilamos SOLO el módulo api-gateway, arrastrando dependencias (-am)
RUN mvn clean package -pl api-gateway -am -DskipTests


# ----------- STAGE 2: RUNTIME -----------
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar el jar del módulo api-gateway construido en el stage anterior
COPY --from=builder /app/api-gateway/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
