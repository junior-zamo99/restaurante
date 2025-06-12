FROM openjdk:21-jdk-slim as builder

WORKDIR /app/msvc-autenticacion

COPY ./pom.xml /app
COPY ./msvc-autenticacion/.mvn ./.mvn
COPY ./msvc-autenticacion/mvnw .
COPY ./msvc-autenticacion/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./msvc-autenticacion/src ./src

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

COPY --from=builder /app/msvc-autenticacion/target/msvc-autenticacion-0.0.1-SNAPSHOT.jar .

EXPOSE 9091

ENTRYPOINT ["java", "-jar", "msvc-autenticacion-0.0.1-SNAPSHOT.jar"]