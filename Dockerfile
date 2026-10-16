# ─── Stage 1: Build ───────────────────────────────────────────────
FROM maven:3.9.14-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy pom.xml first to leverage Docker layer cache for dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and run clean package (skipping tests — remove flag if you want them)
COPY src ./src
RUN mvn clean package -DskipTests -B

# ─── Stage 2: Runtime ─────────────────────────────────────────────
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app
VOLUME /tmp

COPY --from=builder /app/target/StoreMaster-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]