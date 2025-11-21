# ================================
# Etapa 1: Compilar TODO el proyecto
# ================================
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copiamos todo el repositorio (el padre + todos los módulos)
COPY . .

# Compila todos los módulos del multi-módulo
RUN mvn clean install -DskipTests

# ================================
# FIN — El root NO genera imagen final
# ================================
# Este Dockerfile solo compila el multi-módulo completo.
# Cada microservicio usa SU PROPIO Dockerfile para generar su imagen.
