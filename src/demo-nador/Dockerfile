# Etape 1 : Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etape 2 : Run
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# On ne copie que le jar compilé
COPY --from=build /app/target/escale-app.jar app.jar
# Sécurité : utilisateur non-root
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]