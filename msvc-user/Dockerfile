FROM openjdk:17.0
WORKDIR /app
COPY ./target/msvc-user-1.0.0-SNAPSHOT.jar .
EXPOSE 8001
ENTRYPOINT ["java", "-jar", "msvc-user-1.0.0-SNAPSHOT.jar"]