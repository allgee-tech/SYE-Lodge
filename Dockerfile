# syntax=docker/dockerfile:1

# ---- Build stage -----------------------------------------------------------
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /workspace
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw mvnw
COPY mvnw.cmd mvnw.cmd
COPY sye-lodge-common/pom.xml sye-lodge-common/pom.xml
COPY sye-lodge-persistence/pom.xml sye-lodge-persistence/pom.xml
COPY sye-lodge-service/pom.xml sye-lodge-service/pom.xml
COPY sye-lodge-web/pom.xml sye-lodge-web/pom.xml
RUN mvn -B -DskipTests dependency:go-offline
COPY sye-lodge-common/src sye-lodge-common/src
COPY sye-lodge-persistence/src sye-lodge-persistence/src
COPY sye-lodge-service/src sye-lodge-service/src
COPY sye-lodge-web/src sye-lodge-web/src
RUN mvn -B -DskipTests package

# ---- Runtime stage ---------------------------------------------------------
FROM eclipse-temurin:25-jre
WORKDIR /app
# fontconfig and DejaVu fonts let the demo seeder render placeholder photos.
RUN apt-get update \
    && apt-get install -y --no-install-recommends fontconfig fonts-dejavu-core \
    && rm -rf /var/lib/apt/lists/* \
    && groupadd --system sye_lodge && useradd --system --gid sye_lodge sye_lodge \
    && mkdir -p /app/uploads /app/logs && chown -R sye_lodge:sye_lodge /app
COPY docker-entrypoint.sh /usr/local/bin/docker-entrypoint.sh
RUN chmod 0755 /usr/local/bin/docker-entrypoint.sh
COPY --from=build /workspace/sye-lodge-web/target/sye-lodge-web-1.0.0.jar /app/sye_lodge.jar
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=prod \
    PORT=8080 \
    UPLOAD_DIR=/app/uploads \
    LOG_FILE=/app/logs/sye_lodge.log \
    JAVA_TOOL_OPTIONS="-Djava.awt.headless=true"
# Starts as root, fixes mounted-disk ownership, then drops to "sye_lodge".
ENTRYPOINT ["/usr/local/bin/docker-entrypoint.sh"]
