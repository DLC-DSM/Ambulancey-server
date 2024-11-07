FROM ubuntu:latest
FROM bellsoft/liberica-openjdk-alpine:23
FROM openjdk:23-jdk

LABEL authors="user"

VOLUME /tmp

ARG JAR_FILE=./API/build/libs/API-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar


CMD ["./gradlew", "clean", "build"]
ENTRYPOINT ["java", "-jar", "app.jar"]