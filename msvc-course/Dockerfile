FROM openjdk:17-jdk-alpine AS builder

WORKDIR /app/msvc-course

COPY ./pom.xml /app
COPY ./msvc-course/.mvn ./.mvn
COPY ./msvc-course/mvnw .
COPY ./msvc-course/pom.xml .

# either of the following 2 definitions can be used and the other one must be commented upon
#RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
RUN ./mvnw dependency:go-offline

COPY ./msvc-course/src ./src

RUN ./mvnw clean package -Dmaven.test.skip

FROM openjdk:17-jdk-alpine

WORKDIR /app
RUN mkdir ./logs

COPY --from=builder /app/msvc-course/target/msvc-course-1.0.0-SNAPSHOT.jar .

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "msvc-course-1.0.0-SNAPSHOT.jar"]
