#FROM docker.oaplatform.org/oap-jvm:jdk-17-35-debian-buster-slim-36
FROM openjdk:17-jdk-slim

WORKDIR /app

ARG JAR_FILE=target/xpense-service-*.jar

COPY ${JAR_FILE} /app/limebrew/app.jar

ENTRYPOINT ["java", "-jar","/app/limebrew/app.jar"]

EXPOSE 8080