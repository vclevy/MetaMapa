# ==========================
# Etapa 1: Build del JAR con Maven
# ==========================
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copiar TODO el repo (sea cual sea la carpeta que Dokploy genera)
COPY . .

# Detectar automáticamente el POM PADRE (el único que contiene <modules>)
RUN PARENT_POM=$(grep -rl "<modules>" . | head -n 1) && \
    echo "Parent pom encontrado en: $PARENT_POM" && \
    mvn -f $PARENT_POM clean package -pl api-gateway -am -DskipTests

# ==========================
# Etapa 2: Imagen final
# ==========================
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiar el JAR compilado del módulo
COPY --from=builder /app/**/api-gateway/target/*.jar app.jar

EXPOSE 8090
ENTRYPOINT ["java","-jar","app.jar"]
