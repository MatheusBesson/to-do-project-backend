# =========================
# Etapa 1: Build
# =========================
FROM maven:3.9.10-eclipse-temurin-21 AS build
WORKDIR /app

# Copia arquivos do projeto
COPY pom.xml .
COPY src ./src
COPY mvnw .
COPY .mvn .mvn

# Build do projeto sem testes
RUN ./mvnw clean package -DskipTests

# =========================
# Etapa 2: Runtime
# =========================
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copia o JAR gerado na etapa de build
COPY --from=build /app/target/*.jar app.jar

# Porta que o Render passará
ENV PORT=8080
EXPOSE 8080

# Inicia a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
