FROM openjdk:21-jdk-slim as builder

WORKDIR /app/msvc-gateway

COPY ./pom.xml /app
COPY ./msvc-gateway/.mvn ./.mvn
COPY ./msvc-gateway/mvnw .
COPY ./msvc-gateway/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./msvc-gateway/src ./src

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

COPY --from=builder /app/msvc-gateway/target/msvc-gateway-0.0.1-SNAPSHOT.jar .

# PUERTO CORREGIDO: 9090 en lugar de 8090
EXPOSE 9090

ENTRYPOINT ["java", "-jar", "msvc-gateway-0.0.1-SNAPSHOT.jar"]