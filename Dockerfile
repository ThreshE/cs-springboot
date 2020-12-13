FROM openjdk:8u191-jre-alpine3.9
ENTRYPOINT ["/usr/bin/java", "-jar", "/app.jar"]
ARG JAR_FILE
ADD target/cs-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8888