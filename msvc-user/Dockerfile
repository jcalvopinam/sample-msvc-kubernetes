FROM openjdk:17-jdk-alpine AS builder
ARG MSVC_USER=msvc-user
WORKDIR /app/$MSVC_USER

COPY ./pom.xml /app
COPY ./$MSVC_USER/.mvn ./.mvn
COPY ./$MSVC_USER/mvnw .
COPY ./$MSVC_USER/pom.xml .

# either of the following 2 definitions can be used and the other one must be commented upon
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
# RUN ./mvnw dependency:go-offline

COPY ./$MSVC_USER/src ./src

RUN ./mvnw clean package -Dmaven.test.skip

FROM openjdk:17-jdk-alpine

WORKDIR /app
RUN mkdir ./logs
ARG TARGET_FOLDER=/app/msvc-user/target
COPY --from=builder $TARGET_FOLDER/msvc-user-1.0.0-SNAPSHOT.jar .

ENV APP_PORT 8080
EXPOSE $APP_PORT

ENTRYPOINT ["java", "-jar", "msvc-user-1.0.0-SNAPSHOT.jar"]
