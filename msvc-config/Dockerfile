FROM openjdk:21

WORKDIR /app/msvc-config

COPY ./pom.xml /app
COPY ./msvc-config/.mvn ./.mvn
COPY ./msvc-config/mvnw .
COPY ./msvc-config/pom.xml .


RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./msvc-config/src ./src

RUN ./mvnw clean package -DskipTests


EXPOSE 8888

ENTRYPOINT ["java", "-jar", "./target/msvc-config-0.0.1-SNAPSHOT.jar"]