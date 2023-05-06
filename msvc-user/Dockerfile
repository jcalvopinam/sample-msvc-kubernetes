FROM openjdk:17.0

WORKDIR /app/msvc-user

COPY ./pom.xml /app
COPY ./msvc-user/.mvn ./.mvn
COPY ./msvc-user/mvnw .
COPY ./msvc-user/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./msvc-user/src ./src

RUN ./mvnw clean package -Dmaven.test.skip

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "./target/msvc-user-1.0.0-SNAPSHOT.jar"]
