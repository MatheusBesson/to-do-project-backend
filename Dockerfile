# ==============================
# Etapa 1: Build da aplicação
# ==============================
FROM maven:3.9.10 AS build

# Instala Java 22
RUN apt-get update && \
    apt-get install -y openjdk-22-jdk && \
    apt-get clean

WORKDIR /app

# Copia arquivos do projeto
COPY pom.xml .
COPY src ./src
COPY mvnw .
COPY .mvn .mvn

# Build do projeto sem testes
RUN ./mvnw clean package -DskipTests

# ==============================
# Etapa 2: Runtime
# ==============================
FROM openjdk:22-jdk

WORKDIR /app

# Copia o JAR gerado na etapa de build
COPY --from=build /app/target/*.jar app.jar

# Variável de porta para o Render
ENV PORT=8080
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
