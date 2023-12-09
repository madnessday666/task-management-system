FROM gradle:8.4.0-jdk17-alpine AS build
WORKDIR /app
ADD --chown=gradle:gradle . /app/task-management-system
WORKDIR /app/task-management-system
RUN gradle build --no-daemon

FROM openjdk:17-alpine

EXPOSE 8080

WORKDIR /app
COPY --from=build /app/task-management-system/build/libs/*.jar /app/
RUN mv /app/task-management-system-1.0.jar /app/task-management-system.jar

ENTRYPOINT [ "/bin/sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /app/task-management-system.jar"]