# Etapa 1: Build da aplicação
FROM maven:3.9.10-eclipse-temurin-22 AS build
WORKDIR /app

# Copia os arquivos do projeto
COPY pom.xml .
COPY src ./src
COPY mvnw .
COPY .mvn .mvn

# Gera o JAR (sem rodar os testes para agilizar)
RUN ./mvnw clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:22-jdk
WORKDIR /app

# Copia o JAR gerado na etapa de build
COPY --from=build /app/target/*.jar app.jar

# Render define a porta na variável de ambiente PORT
ENV PORT=8080
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
