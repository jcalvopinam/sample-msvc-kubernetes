FROM openjdk:17.0

WORKDIR /app/msvc-course

COPY ./pom.xml /app
COPY ./msvc-course/.mvn ./.mvn
COPY ./msvc-course/mvnw .
COPY ./msvc-course/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./msvc-course/src ./src

RUN ./mvnw clean package -Dmaven.test.skip

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "./target/msvc-course-1.0.0-SNAPSHOT.jar"]
