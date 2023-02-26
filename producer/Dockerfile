FROM amazoncorretto:11-alpine-jdk
COPY /target/producer-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]