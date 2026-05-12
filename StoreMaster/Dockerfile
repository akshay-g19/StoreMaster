# ── Stage 1: Build ──────────────────────────────────────────────
FROM eclipse-temurin:21-jdk-jammy AS builder

WORKDIR /app

# Copy Maven wrapper and pom first (layer cache for dependencies)
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Download dependencies (cached unless pom.xml changes)
RUN ./mvnw dependency:go-offline -B

# Copy source and package (skipping tests)
COPY src/ src/
RUN ./mvnw package -DskipTests -B

# ── Stage 2: Run ────────────────────────────────────────────────
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app
VOLUME /tmp

COPY --from=builder /app/target/StoreMaster-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]