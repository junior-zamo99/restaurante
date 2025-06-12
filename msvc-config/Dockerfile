FROM openjdk:21-jdk-slim as builder

WORKDIR /app/msvc-config

COPY ./pom.xml /app
COPY ./msvc-config/.mvn ./.mvn
COPY ./msvc-config/mvnw .
COPY ./msvc-config/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./msvc-config/src ./src

RUN ./mvnw clean package -DskipTests

# =============================================
# STAGE 2: Runtime con herramientas básicas
# =============================================
FROM openjdk:21-jdk-slim

# Instalar herramientas para health checks
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    wget \
    curl && \
    rm -rf /var/lib/apt/lists/* && \
    apt-get clean

WORKDIR /app

COPY --from=builder /app/msvc-config/target/msvc-config-0.0.1-SNAPSHOT.jar .

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "msvc-config-0.0.1-SNAPSHOT.jar"]